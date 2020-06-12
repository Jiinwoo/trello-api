package me.jung.jwt.demo.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.jung.jwt.demo.api.dto.BoardDTO;
import me.jung.jwt.demo.domain.UserPrincipal;
import me.jung.jwt.demo.domain.user.Email;
import me.jung.jwt.demo.domain.user.User;
import me.jung.jwt.demo.domain.user.UserRepository;
import me.jung.jwt.demo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity makeBoard (@RequestBody BoardDTO.makeReq dto,
                                     @AuthenticationPrincipal UserPrincipal principal) {
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        boardService.makeBoard(dto,user);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<BoardDTO.boardRes> getAllBoards (@AuthenticationPrincipal UserPrincipal principal){
        User user = userRepository.findByEmail(Email.builder().value(principal.getEmail()).build());
        return boardService.getAllBoards(user);
    }
}
