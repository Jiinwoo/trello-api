package me.jung.jwt.demo.api;

import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.api.dto.TaskDTO;
import me.jung.jwt.demo.api.dto.TodoDTO;
import me.jung.jwt.demo.domain.UserPrincipal;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.User;
import me.jung.jwt.demo.domain.user.UserRepository;
import me.jung.jwt.demo.service.TodoService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;
    private final UserRepository userRepository;
    @PostMapping("/")
    public ResponseEntity makeTodo (@RequestBody TodoDTO.makeReq dto,
                                    @RequestParam("boardId") Long boardId,
                                    @AuthenticationPrincipal UserPrincipal principal) {
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        todoService.addTodo(dto,boardId , user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @PostMapping("/{todoId}/tasks/")
    public ResponseEntity makeTask(@RequestBody TaskDTO.makeReq dto,
                                   @PathVariable("todoId") Long todoId,
                                   @RequestParam("boardId") Long boardId,
                                   @AuthenticationPrincipal UserPrincipal principal
    ){
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        todoService.addTask(dto,todoId,boardId,user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
    @GetMapping("/")
    public List<TodoDTO.res> getTodos (@RequestParam("boardId") Long boardId,
                                       @AuthenticationPrincipal UserPrincipal principal
                                       ){
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        return todoService.getTodos(boardId,user);
    }

    @PostMapping("/{boardId}/")
    public ResponseEntity moveTask(
            @RequestBody TaskDTO.moveReq dto,
            @PathVariable("boardId") Long boardId,
            @RequestParam("beforeTodoId") Long beforeTodoId,
            @RequestParam("afterTodoId") Long afterTodoId,
            @AuthenticationPrincipal UserPrincipal principal
    ){
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        todoService.moveTask(dto,boardId,beforeTodoId,afterTodoId,user);
        return new ResponseEntity(HttpStatus.OK);
    }


}
