package me.jung.jwt.demo.service;

import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.api.dto.TaskDTO;
import me.jung.jwt.demo.api.dto.TodoDTO;
import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.Board.BoardRepository;
import me.jung.jwt.demo.domain.todo.Task;
import me.jung.jwt.demo.domain.todo.Todo;
import me.jung.jwt.demo.domain.todo.TodoRepository;
import me.jung.jwt.demo.domain.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final BoardRepository boardRepository;
    @Transactional
    public void addTodo (TodoDTO.makeReq dto , Long boardId , User user) {
        Board board = boardRepository.findByIdAndUser(boardId,user).orElseThrow();
        Todo todo = todoRepository.save(dto.toEntity(board));
        board.addTodo(todo);
    }
    @Transactional
    public void addTask (TaskDTO.makeReq dto, Long todoId,Long boardId, User user) {
        Board board = boardRepository.findByIdAndUser(boardId,user).orElseThrow();
        Todo todo = todoRepository.findByIdAndBoard(todoId,board).orElseThrow();
        todo.addTask(dto.toEntity(todo));
    }
    @Transactional
    public List<TodoDTO.res> getTodos(Long boardId,User user){
        Board board = boardRepository.findByIdAndUser(boardId,user).orElseThrow();
        return board.getTodo()
                .stream()
                .map((Todo todo)->
                        TodoDTO.res.builder()
                                .todoId(todo.getId())
                                .title(todo.getTitle()).tasks(todo.getTasks())
                                .build()).collect(Collectors.toList());
    }
    @Transactional
    public void moveTask(TaskDTO.moveReq dto, Long boardId, Long beforeTodoId, Long afterTodoId ,User user) {
        Board selectedBoard = boardRepository.findByIdAndUser(boardId ,user).orElseThrow();
        Todo beforeTodo = selectedBoard.getTodo().stream().filter((Todo todo)-> todo.getId().equals(beforeTodoId)).collect(Collectors.toList()).get(0);
        Task removedTask = beforeTodo.removeTask(dto.getTaskId());
        Todo afterTodo = selectedBoard.getTodo().stream().filter((Todo todo)-> todo.getId().equals(afterTodoId)).collect(Collectors.toList()).get(0);
        afterTodo.addTask(Task.builder().taskName(removedTask.getTaskName()).todo(afterTodo).build());
        boardRepository.save(selectedBoard);
    }
}
