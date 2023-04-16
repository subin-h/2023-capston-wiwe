package com.capston.wiwe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsCreateRequestDto {

    private String title;
    private String content;
}
