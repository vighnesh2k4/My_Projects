package com.vighnesh.library.controller;

import com.vighnesh.library.pojo.Book;
import com.vighnesh.library.service.BookService;
import com.vighnesh.library.pojo.Book.Availability;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        if (!model.containsAttribute("book")) {
            model.addAttribute("book", new Book());
        }
        model.addAttribute("statuses", Arrays.asList(Book.Status.values()));
        model.addAttribute("availabilities", Arrays.asList(Book.Availability.values()));
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.book", bindingResult);
            redirectAttributes.addFlashAttribute("book", book);
            redirectAttributes.addFlashAttribute("message", "Please correct the errors in the form.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/books/add";
        }
        bookService.addBook(book);
        redirectAttributes.addFlashAttribute("message", "Book added successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable("id") int id, Model model) {
        if (!model.containsAttribute("book")) {
             model.addAttribute("book", bookService.getBookById(id));
        }
        model.addAttribute("statuses", Arrays.asList(Book.Status.values()));
        model.addAttribute("availabilities", Arrays.asList(Book.Availability.values()));
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.book", bindingResult);
            redirectAttributes.addFlashAttribute("book", book);
            redirectAttributes.addFlashAttribute("message", "Please correct the errors in the form.");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/books/edit/" + id;
        }
        book.setBookid(id);
        bookService.updateBookDetails(book);
        redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("message", "Book deleted successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/books";
        
    }

    @GetMapping("/set-availability/{id}")
    public String setBookAvailability(@PathVariable("id") int id,
                                      @RequestParam("status") String availabilityString,
                                      RedirectAttributes redirectAttributes) {
        Availability availability = Availability.valueOf(availabilityString);
        bookService.updateBookAvailability(id, availability);
        redirectAttributes.addFlashAttribute("message", "Book availability updated successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/books";
    }
}