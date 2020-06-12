package me.jung.jwt.demo.domain.todo;

import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByIdAndBoard(Long todoId, Board board);
}
