package com.vighnesh.library.service;

import com.vighnesh.library.pojo.Book;
import com.vighnesh.library.pojo.Book.Availability;
import com.vighnesh.library.pojo.Book.Status;
import com.vighnesh.library.repository.BookRepository;
import com.vighnesh.library.handler.InvalidInputException; 
import com.vighnesh.library.handler.LogicalError;     
import com.vighnesh.library.handler.ResourceNotFoundException; 
import com.vighnesh.library.repository.IssueRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final IssueRecordRepository issueRecordRepository;

    @Autowired
    public BookService(BookRepository bookRepository, IssueRecordRepository issueRecordRepository) {
        this.bookRepository = bookRepository;
        this.issueRecordRepository = issueRecordRepository;
    }

    @Transactional
    public void addBook(Book book) {
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title cannot be empty.");
        }
        bookRepository.addBook(book);
    }

    @Transactional
    public void updateBookDetails(Book book) {
        if (book == null || book.getBookid() == null || book.getBookid() <= 0) {
            throw new InvalidInputException("Book ID must be valid for update.");
        }
        bookRepository.getBookById(book.getBookid())
                      .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + book.getBookid()));

        bookRepository.updateBookDetails(book);
    }

    @Transactional
    public void updateBookAvailability(int bookId, Availability availability) {
        if (bookId <= 0 || availability == null) {
            throw new InvalidInputException("Invalid book ID or availability status.");
        }

        Book book = bookRepository.getBookById(bookId)
                              .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (book.getStatus() == Status.INACTIVE) {
            throw new LogicalError("Cannot change availability for an inactive book.");
        }

        bookRepository.updateBookAvailability(bookId, availability);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getBookById(int bookId) {
        if (bookId <= 0) {
            throw new InvalidInputException("Invalid book ID.");
        }
        return bookRepository.getBookById(bookId)
                             .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
    }

    @Transactional
    public void deleteBook(int bookId) {
        if (bookId <= 0) {
            throw new InvalidInputException("Invalid book ID.");
        }
        bookRepository.getBookById(bookId)
                      .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        if (issueRecordRepository.hasActiveLoansForBook(bookId)) {
            throw new LogicalError("Cannot delete book with active loans. (Delete after book returned)");
        }

        bookRepository.deleteById(bookId);
    }
}