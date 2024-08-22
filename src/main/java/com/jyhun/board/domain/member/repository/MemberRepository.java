package com.jyhun.board.domain.member.repository;

import com.jyhun.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
