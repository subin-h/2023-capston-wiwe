package com.capston.wiwe.service;

import com.capston.wiwe.config.SecurityUtil;
import com.capston.wiwe.dto.community.BoardsCreateDto;
import com.capston.wiwe.dto.community.BoardsRequestDto;
import com.capston.wiwe.dto.community.BoardsDto;
import com.capston.wiwe.entity.community.Boards;
import com.capston.wiwe.entity.user.User;
import com.capston.wiwe.exception.BoardNotFoundException;
import com.capston.wiwe.exception.MemberNotFoundException;
import com.capston.wiwe.repository.BoardsRepository;
import com.capston.wiwe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardsCreateDto createDto (BoardsRequestDto req) { //게시글 작성
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        Boards boards = boardsRepository.save(new Boards(req.getTitle(), req.getContent(), user));
        return BoardsCreateDto.toDto(boards);
    }
    @Transactional(readOnly = true)
    public BoardsDto findBoards(Long id){ //게시글 단건 조회

        Boards boards = boardsRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        User writer = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        return BoardsDto.toDto(boards, writer.getNickname());
    }

    @Transactional
    public BoardsDto updateDto (Long id, BoardsRequestDto req){ //게시글 수정
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

}
