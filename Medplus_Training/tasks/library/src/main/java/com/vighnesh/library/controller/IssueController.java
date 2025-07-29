package com.vighnesh.library.controller;

import com.vighnesh.library.pojo.IssueRecord;
import com.vighnesh.library.service.BookService;
import com.vighnesh.library.service.IssueService;
import com.vighnesh.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final BookService bookService;
    private final MemberService memberService;

    @Autowired
    public IssueController(IssueService issueService, BookService bookService, MemberService memberService) {
        this.issueService = issueService;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    @GetMapping
    public String getAllIssuedRecords(Model model) {
        model.addAttribute("issueRecords", issueService.getAllIssuedRecords());
        return "issues/list";
    }

    @GetMapping("/issue")
    public String showIssueBookForm(Model model) {
        model.addAttribute("issueRecord", new IssueRecord());
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("members", memberService.getAllMembers());
        return "issues/issue";
    }

    @PostMapping("/issue")
    public String issueBook(@RequestParam("bookid") int bookId,
                            @RequestParam("memberid") int memberId,
                            RedirectAttributes redirectAttributes) {
        issueService.issueBook(bookId, memberId);
        redirectAttributes.addFlashAttribute("message", "Book issued successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/issues";
    }

    @GetMapping("/return")
    public String showReturnBookForm(Model model) {
        model.addAttribute("issueRecords", issueService.getAllIssuedRecords());
        return "issues/return";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("issueid") int issueId,
                             @RequestParam("bookid") int bookId,
                             RedirectAttributes redirectAttributes) {
        issueService.returnBook(issueId, bookId);
        redirectAttributes.addFlashAttribute("message", "Book returned successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/issues";
    }
}