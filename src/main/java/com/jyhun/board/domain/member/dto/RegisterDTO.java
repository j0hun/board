package com.jyhun.board.domain.member.dto;


import com.jyhun.board.domain.member.constant.Role;
import com.jyhun.board.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    private String name;

    private String email;

    private String password;

    public Member toEntity(Member member) {
        return new Member(name, email, password, Role.USER);
    }

}
