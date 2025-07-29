package com.vighnesh.library.service;

import com.vighnesh.library.pojo.Member;
import com.vighnesh.library.repository.MemberRepository;
import com.vighnesh.library.repository.IssueRecordRepository;
import com.vighnesh.library.handler.InvalidInputException;  
import com.vighnesh.library.handler.LogicalError;      
import com.vighnesh.library.handler.ResourceNotFoundException; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final IssueRecordRepository issueRecordRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, IssueRecordRepository issueRecordRepository) {
        this.memberRepository = memberRepository;
        this.issueRecordRepository = issueRecordRepository;
    }

    @Transactional
    public void addMember(Member member) {
        if (member == null || member.getName() == null || member.getName().trim().isEmpty()) {
            throw new InvalidInputException("Member name cannot be empty.");
        }
        if (memberRepository.getMemberByEmail(member.getEmail()).isPresent()) {
            throw new LogicalError("Member with email " + member.getEmail() + " already exists.");
        }
        memberRepository.addMember(member);
    }

    @Transactional
    public Member updateMember(Member member) {
        if (member == null || member.getMemberId() == null || member.getMemberId() <= 0) {
            throw new InvalidInputException("Member ID must be valid for update.");
        }
        @SuppressWarnings("unused")
		Member existingMember = memberRepository.getMemberById(member.getMemberId())
                            .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + member.getMemberId()));

        Optional<Member> memberWithSameEmail = memberRepository.getMemberByEmail(member.getEmail());
        if (memberWithSameEmail.isPresent() && !memberWithSameEmail.get().getMemberId().equals(member.getMemberId())) {
            throw new LogicalError("Another member with email " + member.getEmail() + " already exists.");
        }

        memberRepository.updateMember(member);
        return member;
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAllMembers();
    }

    public Member getMemberById(int memberId) {
        if (memberId <= 0) {
            throw new InvalidInputException("Invalid member ID.");
        }
        return memberRepository.getMemberById(memberId)
                               .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));
    }

    @Transactional
    public void deleteMember(int memberId) {
        if (memberId <= 0) {
            throw new InvalidInputException("Invalid member ID.");
        }
        memberRepository.getMemberById(memberId)
                        .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + memberId));

        if (issueRecordRepository.hasActiveLoansForMember(memberId)) {
            throw new LogicalError("Cannot delete member with outstanding loans. (Return the borrowed books)");
        }

        memberRepository.deleteById(memberId);
    }
}