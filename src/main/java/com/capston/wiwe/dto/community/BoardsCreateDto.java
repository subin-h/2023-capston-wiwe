package com.capston.wiwe.dto.community;

import com.capston.wiwe.entity.community.Boards;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsCreateDto {

    private Long id;
    private String title;
    private String content;

    public static BoardsCreateDto toDto(Boards boards) {
        return new BoardsCreateDto(boards.getBoardsId(), boards.getBoardsTitle(), boards.getBoardsContent());
    }


}
