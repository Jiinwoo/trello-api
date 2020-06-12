package me.jung.jwt.demo.domain.todo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.BaseTimeEntity;

import javax.persistence.*;

@Embeddable
@Getter
@Entity
@NoArgsConstructor
public class Task  extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String taskName;

    @ManyToOne
    @JoinColumn(name = "TODO_ID",nullable = false,updatable = false)
    private Todo todo;

    @Builder
    public Task(String taskName,Todo todo) {
        this.taskName = taskName;
        this.todo = todo;
    }
}
