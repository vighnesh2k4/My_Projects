package com.vighnesh.library.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vighnesh.library.pojo.IssueRecord;
import com.vighnesh.library.pojo.IssueRecord.Status;
import com.vighnesh.library.utility.DBqueries;

@Repository
public class IssueRecordRepository {
	
	class IssueRecordRowMapper implements RowMapper<IssueRecord>{
		@Override
		public IssueRecord mapRow(ResultSet rs, int IssueId) throws SQLException{
			IssueRecord record = new IssueRecord();
	        record.setIssueid(rs.getInt("issueid"));
	        record.setBookid(rs.getInt("bookid"));
	        record.setMemberid(rs.getInt("memberid"));
	        String statusStr = rs.getString("status");
	        if ("I".equals(statusStr)) {
	            record.setStatus(Status.ISSUED);
	        } else if ("R".equals(statusStr)) {
	            record.setStatus(Status.RETURNED);
	        } else {
	            record.setStatus(Status.RETURNED);
	        }
	        record.setIssuedate(rs.getDate("issuedate"));
	        record.setReturndate(rs.getDate("returndate"));
	        return record;
		}
    }
	
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IssueRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void issueBook(IssueRecord issueRecord) {
    	jdbcTemplate.update(DBqueries.INSERT_ISSUE_RECORD,issueRecord.getBookid(),issueRecord.getMemberid(),
    			new java.sql.Date(issueRecord.getIssuedate().getTime()));
    }

    public void returnBook(int issueId, Date returnDate) {
        jdbcTemplate.update(DBqueries.UPDATE_ISSUE_STATUS_RETURN,
                new java.sql.Date(returnDate.getTime()),issueId);
    }

    public List<IssueRecord> getAllIssuedRecords() {
        return jdbcTemplate.query(DBqueries.SELECT_ALL_ISSUED_RECORDS, new IssueRecordRowMapper());
    }

    public Optional<IssueRecord> getIssueRecordById(int issueId) {
        try {
            IssueRecord record = jdbcTemplate.queryForObject(DBqueries.SELECT_ISSUE_RECORD_BY_ID, new IssueRecordRowMapper(), issueId);
            return Optional.ofNullable(record);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean hasActiveLoansForBook(int bookId) {
        Integer count = jdbcTemplate.queryForObject(DBqueries.SELECT_ACTIVE_LOANS_FOR_BOOK, Integer.class, bookId);
        return count != null && count > 0;
    }

    public boolean hasActiveLoansForMember(int memberId) {
        Integer count = jdbcTemplate.queryForObject(DBqueries.SELECT_ACTIVE_LOANS_FOR_MEMBER, Integer.class, memberId);
        return count != null && count > 0;
    }
}