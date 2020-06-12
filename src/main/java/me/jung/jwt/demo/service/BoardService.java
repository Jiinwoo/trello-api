package me.jung.jwt.demo.service;

import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.api.dto.BoardDTO;
import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.Board.BoardRepository;
import me.jung.jwt.demo.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void makeBoard (BoardDTO.makeReq dto, User user) {
        boardRepository.save(dto.toEntity(user));
    }

    public List<BoardDTO.boardRes> getAllBoards (User user) {
        return boardRepository.findByUser(user)
                .stream()
                .map((Board board) -> new BoardDTO.boardRes(board.getContent(),board.getId()))
                .collect(Collectors.toList());
    }

}
