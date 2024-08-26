package com.jyhun.board.domain.member.service;

import com.jyhun.board.domain.member.dto.RegisterDTO;
import com.jyhun.board.domain.member.entity.Member;
import com.jyhun.board.domain.member.repository.MemberRepository;
import com.jyhun.board.global.exception.CustomException;
import com.jyhun.board.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(RegisterDTO registerDTO) {
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Member member = registerDTO.toEntity();
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail()).ifPresent(existingMember -> {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATE);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> {
                    log.error("email이 {}인 회원을 찾을 수 없습니다.", email);
                    return new CustomException(ErrorCode.MEMBER_NOT_FOUND);
                });
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
