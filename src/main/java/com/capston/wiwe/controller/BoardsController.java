package com.capston.wiwe.controller;

import com.capston.wiwe.dto.community.BoardsRequestDto;
import com.capston.wiwe.response.Response;
import com.capston.wiwe.service.BoardsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        return Response.success(boardsService.createBoard(req));
    }

    @ApiOperation(value = "게시글 단건 조회", notes = "게시글을 단건 조회합니다")
    @GetMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response findBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        return Response.success(boardsService.findBoards(id));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editBoard(@PathVariable Long id, @Valid @RequestBody BoardsRequestDto req) {
        return Response.success(boardsService.updateBoard(id, req));
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteBoard(@PathVariable Long id) {
        boardsService.deleteBoard(id);
        return Response.success(null);
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회합니다.")
    @GetMapping("/boards")
    @ResponseStatus(HttpStatus.OK) //sort 변수 이름 주의!
    public Response findAllPage(@PageableDefault(size = 5, sort = "boardsId", direction = Sort.Direction.DESC) Pageable pageable) {
        // ex) http://localhost:8080/boards/boards/?page=0
        return Response.success(boardsService.findAllPage(pageable));
    }

    @ApiOperation(value = "게시글 검색", notes = "게시글을 검색합니다.")
    @GetMapping("/boards/search")
    @ResponseStatus(HttpStatus.OK)
    public Response search(String keyword, @PageableDefault(size = 5, sort = "boardsId", direction = Sort.Direction.DESC) Pageable pageable) {
        // ex) http://localhost:8080/boards/boards/search?page=0
        return Response.success(boardsService.search(keyword, pageable));
    }



}
