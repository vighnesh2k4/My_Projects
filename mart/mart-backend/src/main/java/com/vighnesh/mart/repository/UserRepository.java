package com.vighnesh.mart.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.vighnesh.mart.helper.UserRowMapper;
import com.vighnesh.mart.pojo.User;
import com.vighnesh.mart.utility.DbQueries;

@Repository
public class UserRepository {
	
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
    public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
	public void addUser(User user) {
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("username", user.getUsername())
			.addValue("email", user.getEmail())
			.addValue("password", user.getPassword());
		namedParameterJdbcTemplate.update(DbQueries.INSERT_USER, params);
	}

	public void updateUser(User user){
		int usernameFlag=user.getUsername()!=null?1:0;
		int emailFlag=user.getEmail()!=null?1:0;
		int passwordFlag=user.getPassword()!=null?1:0;
		int roleFlag=user.getRole()!=null?1:0;
		int statusFlag=user.getStatus()!=null?1:0;
		
		StringBuilder sql = new StringBuilder("UPDATE users SET ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List<String> setClauses = new ArrayList<>();
		if (usernameFlag == 1) {
		    setClauses.add("username = :username");
		    params.addValue("username", user.getUsername());
		}
		if (emailFlag == 1) {
		    setClauses.add("email = :email");
		    params.addValue("email", user.getEmail());
		}
		if (passwordFlag == 1) {
		    setClauses.add("password = :password");
		    params.addValue("password", user.getPassword());
		}
		if (roleFlag == 1) {
		    setClauses.add("role = :role");
		    params.addValue("role", user.getRole().name());
		}
		if (statusFlag == 1) {
		    setClauses.add("status = :status");
		    params.addValue("status", user.getStatus().name());
		}
		if (setClauses.isEmpty()) {
		    throw new IllegalArgumentException("No fields to update.");
		}

		sql.append(String.join(", ", setClauses));
		sql.append(" WHERE user_id = :user_id");
		params.addValue("user_id", user.getUser_id());

		namedParameterJdbcTemplate.update(sql.toString(), params);
	}

	public List<User> getUser(User user) {
		int userIdFlag=(user.getUser_id() > 0)?1:0;
		int usernameFlag=user.getUsername()!=null?1:0;
		int passwordFlag=user.getPassword()!=null?1:0;
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("user_id", user.getUser_id())
			.addValue("username", user.getUsername())
			.addValue("password", user.getPassword())
			.addValue("userIdFlag", userIdFlag)
			.addValue("usernameFlag", usernameFlag)
			.addValue("passwordFlag", passwordFlag);
		return namedParameterJdbcTemplate.query(DbQueries.GET_USER, params, new UserRowMapper());
	}
}
