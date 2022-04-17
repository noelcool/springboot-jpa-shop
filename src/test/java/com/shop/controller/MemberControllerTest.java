package com.shop.controller;


import com.shop.domain.dto.MemberFormDto;
import com.shop.domain.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password) {
        MemberFormDto dto = new MemberFormDto();
        dto.setEmail(email);
        dto.setName("noel");
        dto.setAddress("서울시 영등포구 의사당대로83");
        dto.setPassword(password);
        Member member = Member.createMember(dto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@jungyin.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(
                formLogin().userParameter("email").
                loginProcessingUrl("/members/login").
                user(email).
                password(password)).
                andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

}
