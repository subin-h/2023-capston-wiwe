package com.capston.wiwe.repository;

import com.capston.wiwe.entity.community.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardsRepository extends JpaRepository<Boards, Long> {
    Page<Boards> findAll(Pageable pageable);
    List<Boards> findByBoardsTitleContaining(String keyword, Pageable pageable); // findBy 변수 이름 정하기 주의!


}
