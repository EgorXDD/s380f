package com.example.s380f_gp_xd.repository;

import com.example.s380f_gp_xd.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}