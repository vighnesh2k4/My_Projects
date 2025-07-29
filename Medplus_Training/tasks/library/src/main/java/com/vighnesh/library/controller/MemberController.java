package com.vighnesh.library.controller;

import com.vighnesh.library.pojo.Member;
import com.vighnesh.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String getAllMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "members/list";
    }

    @GetMapping("/add")
    public String showAddMemberForm(Model model) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", new Member());
        }
        model.addAttribute("genders", Arrays.asList(Member.Gender.values()));
        return "members/add";
    }

    @PostMapping("/add")
    public String addMember(@Valid @ModelAttribute("member") Member member,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.member", bindingResult);
            redirectAttributes.addFlashAttribute("member", member);
            redirectAttributes.addFlashAttribute("genders", Arrays.asList(Member.Gender.values()));
            redirectAttributes.addFlashAttribute("message", "Please correct the errors in the form.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/members/add";
        }
        memberService.addMember(member);
        redirectAttributes.addFlashAttribute("message", "Member added successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditMemberForm(@PathVariable("id") int id, Model model) {
        if (!model.containsAttribute("member")) {
            model.addAttribute("member", memberService.getMemberById(id));
        }
        model.addAttribute("genders", Arrays.asList(Member.Gender.values()));
        return "members/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateMember(@PathVariable("id") int id,
                               @Valid @ModelAttribute("member") Member member,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.member", bindingResult);
            redirectAttributes.addFlashAttribute("member", member);
            redirectAttributes.addFlashAttribute("message", "Please correct the errors in the form.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/members/edit/" + id;
        }
        member.setMemberId(id);
        memberService.updateMember(member);
        redirectAttributes.addFlashAttribute("message", "Member updated successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/members";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("message", "Member deleted successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/members";
    }
}