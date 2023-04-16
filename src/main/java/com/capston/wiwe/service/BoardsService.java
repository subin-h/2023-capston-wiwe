package com.capston.wiwe.service;

import com.capston.wiwe.config.SecurityUtil;
import com.capston.wiwe.dto.BoardsCreateDto;
import com.capston.wiwe.dto.BoardsCreateRequestDto;
import com.capston.wiwe.dto.BoardsDto;
import com.capston.wiwe.entity.community.Boards;
import com.capston.wiwe.entity.user.User;
import com.capston.wiwe.exception.BoardNotFoundException;
import com.capston.wiwe.exception.MemberNotFoundException;
import com.capston.wiwe.repository.BoardsRepository;
import com.capston.wiwe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@RequiredArgsConstructor
@Service
public class BoardsService {

    private final BoardsRepository boardsRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardsCreateDto createDto (BoardsCreateRequestDto req) { //게시글 작성
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        Boards boards = boardsRepository.save(new Boards(req.getTitle(), req.getContent(), user));
        return BoardsCreateDto.toDto(boards);
    }
    @Transactional(readOnly = true)
    public BoardsDto findBoards(Long id){ //게시글 단건 조회
        User user = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);

        Boards boards = boardsRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);
        User writer = SecurityUtil.getCurrentUsername().flatMap(userRepository::findByUsername).orElse(null);
        return BoardsDto.toDto(boards, writer.getNickname());
    }

}
