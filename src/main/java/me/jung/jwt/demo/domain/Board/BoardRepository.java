package me.jung.jwt.demo.domain.Board;

import me.jung.jwt.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findByUser(User user);
    Optional<Board> findByIdAndUser (Long boardId, User user);
}
