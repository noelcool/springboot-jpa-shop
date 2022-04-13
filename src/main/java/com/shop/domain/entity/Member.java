package com.shop.domain.entity;

import com.shop.constant.Role;
import com.shop.domain.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) // enum을 사용할 때 기본적으로 순서가 저장되는데 enum의 순서가 바뀔 경우 문제가 발생할 수 있으므로 EnumType.STRING 옵션을 사용해서 String으로 저장하는것을 권장
    private Role role; // enum 타입을 엔티티의 속성으로 지정

    public static Member createMember(MemberFormDto dto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setAddress(dto.getAddress());
        String password = passwordEncoder.encode(dto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
