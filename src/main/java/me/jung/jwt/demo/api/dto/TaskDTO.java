package me.jung.jwt.demo.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.todo.Task;
import me.jung.jwt.demo.domain.todo.Todo;

public class TaskDTO {

    @NoArgsConstructor
    @Getter
    public static class makeReq {
        private String taskName;
        public Task toEntity (Todo todo) {
            return Task.builder().todo(todo).taskName(this.taskName).build();
        }
    }
    @NoArgsConstructor
    @Getter
    public static class moveReq {
        private Long taskId;
        private String taskName;
    }

}
