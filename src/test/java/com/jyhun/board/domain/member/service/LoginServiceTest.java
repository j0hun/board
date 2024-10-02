package com.jyhun.board.domain.member.service;

import com.jyhun.board.domain.member.dto.RegisterDTO;
import com.jyhun.board.domain.member.entity.Member;
import com.jyhun.board.domain.member.repository.MemberRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoginService loginService;

    @Test
    @DisplayName("회원 등록 성공")
    void register_Success() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@email.com");
        registerDTO.setName("testName");
        registerDTO.setPassword("test1234");

        // when
        Member member = loginService.register(registerDTO);

        // then
        assertNotNull(member);
        assertEquals("test@email.com", member.getEmail());
        assertEquals("testName", member.getName());
    }

    @Test
    @DisplayName("회원 등록 실패: 중복된 이메일")
    void register_DuplicateEmail() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@email.com");
        registerDTO.setName("testName");
        registerDTO.setPassword("test1234");

        // when
        Member member = loginService.register(registerDTO);

        // then
        CustomException exception = assertThrows(CustomException.class, () -> {
            loginService.register(registerDTO);
        });
        assertEquals(ErrorCode.MEMBER_DUPLICATE, exception.getErrorCode());
    }

    @Test
    @DisplayName("회원 조회 성공")
    void loadUserByUsername_Success() {
        // given
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@email.com");
        registerDTO.setName("testName");
        registerDTO.setPassword("test1234");
        loginService.register(registerDTO);

        // when
        UserDetails userDetails = loginService.loadUserByUsername("test@email.com");

        // then
        assertNotNull(userDetails);
        assertEquals("test@email.com", userDetails.getUsername());
    }

    @Test
    @DisplayName("회원 조회 실패: 존재하지 않는 이메일")
    void loadUserByUsername_NotFound() {
        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            loginService.loadUserByUsername("test@email.com");
        });
        assertEquals(ErrorCode.MEMBER_NOT_FOUND, exception.getErrorCode());
    }

}