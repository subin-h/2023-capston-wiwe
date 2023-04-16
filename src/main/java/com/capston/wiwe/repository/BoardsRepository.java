package com.capston.wiwe.repository;

import com.capston.wiwe.entity.community.Boards;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardsRepository extends JpaRepository<Boards, Long> {
    Page<Boards> findAll(Pageable pageable); // 페이징 처리


}
