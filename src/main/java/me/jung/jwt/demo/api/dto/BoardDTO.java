package me.jung.jwt.demo.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.jung.jwt.demo.domain.Board.Board;
import me.jung.jwt.demo.domain.user.User;

import java.util.List;

public class BoardDTO {

    @Getter
    @NoArgsConstructor
    public static class makeReq {
        private String content;
        @Builder
        public makeReq(String content) {
            this.content = content;
        }

        public Board toEntity(User user) {
            return Board
                    .builder()
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }



    @Getter
    @NoArgsConstructor
    public static class boardRes {
        private Long boardId;
        private String content;

        public boardRes(String content,Long boardId) {
            this.content = content;
            this.boardId = boardId;
        }
    }


}
