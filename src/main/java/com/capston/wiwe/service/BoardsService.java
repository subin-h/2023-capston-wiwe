package com.capston.wiwe.service;

import com.capston.wiwe.config.SecurityUtil;
import com.capston.wiwe.dto.community.BoardsCreateDto;
import com.capston.wiwe.dto.community.BoardsMainDto;
import com.capston.wiwe.dto.community.BoardsRequestDto;
import com.capston.wiwe.dto.community.BoardsDto;
import com.capston.wiwe.entity.community.Boards;
import com.capston.wiwe.entity.user.User;
import com.capston.wiwe.exception.BoardNotFoundException;
import com.capston.wiwe.exception.MemberNotFoundException;
import com.capston.wiwe.repository.BoardsRepository;
import com.capston.wiwe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardsCreateDto createBoard (BoardsRequestDto req) { //게시글 작성
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        Boards boards = boardsRepository.save(new Boards(req.getTitle(), req.getContent(), user));
        return BoardsCreateDto.toDto(boards);
    }
    @Transactional(readOnly = true)
    public BoardsDto findBoards(Long id){ //게시글 단건 조회

        Boards boards = boardsRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        User user = boards.getUser();
        return BoardsDto.toDto(boards, user.getNickname());
    }

    @Transactional
    public BoardsDto updateBoard (Long id, BoardsRequestDto req){ //게시글 수정
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        Boards boards = boardsRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        if(user != boards.getUser()) {
            throw new MemberNotFoundException();
        }
        boards.setBoardsTitle(req.getTitle());
        boards.setBoardsContent(req.getContent());

        return BoardsDto.toDto(boards, user.getNickname());
    }

    @Transactional
    public void deleteBoard(Long id) { //게시물 삭제
        Boards boards = boardsRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);

        if(user != boards.getUser()){
            throw new MemberNotFoundException();
        }
        boardsRepository.delete(boards);
    }

    @Transactional(readOnly = true)
    public List<BoardsMainDto> findAllPage() {
        List<Boards> boards = boardsRepository.findAll();
        List<BoardsMainDto> boardsMainDtoList = new ArrayList<>();
        boards.stream().forEach(i -> boardsMainDtoList.add(new BoardsMainDto().toDto(i)));
        return boardsMainDtoList;
    }

    @Transactional(readOnly = true)
    public List<BoardsMainDto> search(String keyword) {
        List<Boards> boards = boardsRepository.findByBoardsTitleContaining(keyword);
        List<BoardsMainDto> boardSimpleDtoList = new ArrayList<>();
        boards.stream().forEach(i -> boardSimpleDtoList.add(new BoardsMainDto().toDto(i)));
        return boardSimpleDtoList;
    }



}

