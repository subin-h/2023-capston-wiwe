package com.capston.wiwe.controller;

import com.capston.wiwe.dto.community.BoardsRequestDto;
import com.capston.wiwe.response.Response;
import com.capston.wiwe.service.BoardsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardsController {

    private final BoardsService boardsService;
    @ApiOperation(value = "게시글 생성", notes = "게시글 작성")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody BoardsRequestDto req) throws Exception{
        return Response.success(boardsService.createDto(req));
    }

    @ApiOperation(value = "게시글 단건 조회", notes = "게시글을 단건 조회합니다")
    @GetMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response findBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable long id) {
        return Response.success(boardsService.findBoards(id));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editBoard(@PathVariable long id, @Valid @RequestBody BoardsRequestDto req) {
        return Response.success(boardsService.updateDto(id, req));
    }


}
