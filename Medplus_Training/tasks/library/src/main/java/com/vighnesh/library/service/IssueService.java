package com.vighnesh.library.service;

import com.vighnesh.library.pojo.Book;
import com.vighnesh.library.pojo.Book.Availability;
import com.vighnesh.library.pojo.IssueRecord;
import com.vighnesh.library.pojo.IssueRecord.Status;
import com.vighnesh.library.repository.BookRepository;
import com.vighnesh.library.repository.IssueRecordRepository;
import com.vighnesh.library.repository.MemberRepository;
import com.vighnesh.library.handler.InvalidInputException;
import com.vighnesh.library.handler.LogicalError;   
import com.vighnesh.library.handler.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IssueService {
    private final IssueRecordRepository issueRecordRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public IssueService(IssueRecordRepository issueRecordRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.issueRecordRepository = issueRecordRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void issueBook(int bookId, int memberId) {
        if (bookId <= 0 || memberId <= 0) {
            throw new InvalidInputException("Book ID and Member ID must be positive integers.");
        }

        Book book = bookRepository.getBookById(bookId)
                              .orElseThrow(() -> new ResourceNotFoundException("Book with ID " + bookId + " not found."));
        memberRepository.getMemberById(memberId)
                           .orElseThrow(() -> new ResourceNotFoundException("Member with ID " + memberId + " not found."));


        if (book.getStatus() == Book.Status.INACTIVE) {
            throw new LogicalError("Book with ID " + bookId + " is inactive and cannot be issued.");
        }
        if (book.getAvailability() == Availability.ISSUED) {
            throw new LogicalError("Book with ID " + bookId + " is already issued.");
        }

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setBookid(bookId);
        issueRecord.setMemberid(memberId);
        issueRecord.setIssuedate(new Date());
        issueRecord.setStatus(Status.ISSUED);
        issueRecordRepository.issueBook(issueRecord);
        bookRepository.updateBookAvailability(bookId, Availability.ISSUED);
    }

    @Transactional
    public void returnBook(int issueId, int bookId) {
        if (issueId <= 0 || bookId <= 0) {
            throw new InvalidInputException("Issue ID and Book ID must be positive integers.");
        }

        IssueRecord issueRecord = issueRecordRepository.getIssueRecordById(issueId)
                                                   .orElseThrow(() -> new ResourceNotFoundException("Issue record with ID " + issueId + " not found."));

        if (issueRecord.getStatus() == Status.RETURNED) {
            throw new LogicalError("Book for issue ID " + issueId + " has already been returned.");
        }
        if (!issueRecord.getBookid().equals(bookId)) {
            throw new InvalidInputException("Provided Book ID does not match the book in the issue record.");
        }

        Book book = bookRepository.getBookById(bookId)
                                  .orElseThrow(() -> new ResourceNotFoundException("Associated book not found with ID: " + bookId));
        if (book.getAvailability() == Availability.AVAILABLE) {
            throw new LogicalError("Book with ID " + bookId + " is already marked as AVAILABLE, but issue record is not returned.");
        }

        issueRecordRepository.returnBook(issueId, new Date());
        bookRepository.updateBookAvailability(bookId, Availability.AVAILABLE);
    }

    public List<IssueRecord> getAllIssuedRecords() {
        return issueRecordRepository.getAllIssuedRecords();
    }

    public IssueRecord getIssueRecordById(int issueId) {
        if (issueId <= 0) {
            throw new InvalidInputException("Invalid issue ID.");
        }
        return issueRecordRepository.getIssueRecordById(issueId)
                                .orElseThrow(() -> new ResourceNotFoundException("Issue record not found with ID: " + issueId));
    }
}