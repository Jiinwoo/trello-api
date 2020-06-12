package me.jung.jwt.demo.api.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.todo.Task;
import me.jung.jwt.demo.domain.todo.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TodoDTO {
    @Getter
    @NoArgsConstructor
    public static class makeReq {
        private String title;
        public Todo toEntity (Board board) {
            return Todo.builder().title(this.title).board(board).build();
        }
    }
    @Getter
    @NoArgsConstructor
    public static class res {
        private Long todoId;
        private String title;
        private List<taskRes> tasks;

        @Builder
        public res(Long todoId,String title, List<Task> tasks) {
            this.todoId = todoId;
            this.title = title;
            this.tasks = tasks
                    .stream()
                    .map((Task task)->
                            taskRes.builder()
                                    .taskId(task.getId())
                                    .taskName(task.getTaskName())
                                    .build())
                    .collect(Collectors.toList());

        }
    }
    @Getter
    @NoArgsConstructor
    public static class taskRes {
        private Long taskId;
        private String taskName;

        @Builder
        public taskRes(String taskName,Long taskId) {
            this.taskName = taskName;
            this.taskId = taskId;
        }
    }
}
