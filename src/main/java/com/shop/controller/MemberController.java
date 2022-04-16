package com.shop.controller;

import com.shop.domain.dto.MemberFormDto;
import com.shop.domain.entity.Member;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

//    @PostMapping(value = "/new")
//    public String memberForm(MemberFormDto dto) {
//        Member member = Member.createMember(dto, passwordEncoder);
//        memberService.saveMember(member);
//        return "redirect:/";
//    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto dto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(dto, passwordEncoder);
            memberService.saveMember(member);
        } catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }
}
