package com.vighnesh.mart.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vighnesh.mart.pojo.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int agr1) throws SQLException {
		User user = new User();
		user.setUser_id(rs.getInt("user_id"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setRole(User.Role.valueOf(rs.getString("role")));
		user.setStatus(User.Status.valueOf(rs.getString("status")));
		user.setCreated_at(rs.getTimestamp("created_at"));
		return user;
	}
}