package com.capston.wiwe.dto.community;

import com.capston.wiwe.entity.community.Boards;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsDto {

    private Long boardsId;
    private String boardsWriter;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public static BoardsDto toDto(Boards boards, String boardsWriter) {
        return new BoardsDto(
                boards.getBoardsId(),
                boardsWriter,
                boards.getBoardsTitle(),
                boards.getBoardsContent(),
                boards.getCreatedAt()
        );
    }
}
