package me.jung.jwt.demo.domain.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.todo.Todo;
import me.jung.jwt.demo.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table
@NoArgsConstructor
@Entity
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID",nullable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval =  true)
    private List<Todo> todo = new ArrayList<>();

    @Builder
    public Board(String content,User user) {
        this.content = content;
        this.user = user;
        user.addBoard(this);
    }
    public void addTodo(Todo todo){
        this.todo.add(todo);
    }
}
