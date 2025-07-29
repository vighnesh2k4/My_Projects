package com.vighnesh.library.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vighnesh.library.pojo.Member;
import com.vighnesh.library.pojo.Member.Gender;
import com.vighnesh.library.utility.DBqueries;

@Repository
public class MemberRepository {
	
	class MemberRowMapper implements RowMapper<Member>{
		@Override
		public Member mapRow(ResultSet rs, int memberId) throws SQLException{
			Member member = new Member();
	        member.setMemberId(rs.getInt("memberid"));
	        member.setName(rs.getString("name"));
	        member.setEmail(rs.getString("email"));
	        member.setMobile(rs.getLong("mobile"));
	        member.setGender(Gender.valueOf(rs.getString("gender").equals("M") ? "MALE" : "FEMALE"));
	        member.setAddress(rs.getString("address"));
	        return member;
		}
    }
	
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addMember(Member member) {
    	jdbcTemplate.update(DBqueries.INSERT_MEMBER,
                member.getName(),
                member.getEmail(),
                member.getMobile(),
                member.getGender().toString().substring(0, 1),
                member.getAddress());
    }

    public void updateMember(Member member) {
        jdbcTemplate.update(DBqueries.UPDATE_MEMBER,
                member.getName(),
                member.getEmail(),
                member.getMobile(),
                member.getGender().toString().substring(0, 1),
                member.getAddress(),
                member.getMemberId());
    }

    public List<Member> getAllMembers() {
        return jdbcTemplate.query(DBqueries.SELECT_ALL_MEMBERS, new MemberRowMapper());
    }

    public Optional<Member> getMemberById(int memberId) {
        try {
            Member member = jdbcTemplate.queryForObject(DBqueries.SELECT_MEMBER_BY_ID, new MemberRowMapper(), memberId);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> getMemberByEmail(String email) {
        try {
            Member member = jdbcTemplate.queryForObject(DBqueries.SELECT_MEMBER_BY_EMAIL,new MemberRowMapper(), email);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void deleteById(int memberId) {
        jdbcTemplate.update(DBqueries.DELETE_MEMBER, memberId);
    }
}