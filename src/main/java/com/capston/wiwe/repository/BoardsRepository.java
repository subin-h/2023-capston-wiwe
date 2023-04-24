package com.capston.wiwe.repository;

import com.capston.wiwe.entity.community.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardsRepository extends JpaRepository<Boards, Long> {
    List<Boards> findAll();
    List<Boards> findByBoardsTitleContaining(String keyword); // findBy 변수 이름 정하기 주의!


}
