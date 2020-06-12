package me.jung.jwt.demo.domain.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.BaseTimeEntity;
import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.user.User;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@Table
@Entity
public class Todo  extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TODO_ID")
    private Long id;

    @Column
    private String title;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval =  true)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    private Board board;

    @Builder
    public Todo(String title, Board board) {
        this.title = title;
        this.board = board;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public Task removeTask(Long taskId) {
        Task first = this.tasks.stream().filter((Task task) -> task.getId().equals(taskId)).findFirst().orElseThrow();
         this.tasks.remove(first);
         return first;
    }
}
