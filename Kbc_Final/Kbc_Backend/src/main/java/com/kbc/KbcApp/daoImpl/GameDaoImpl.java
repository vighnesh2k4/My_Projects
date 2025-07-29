
package com.kbc.KbcApp.daoImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kbc.KbcApp.dao.GameDao;
import com.kbc.KbcApp.helper.GameRowMapper;
import com.kbc.KbcApp.pojos.Game;
import com.kbc.KbcApp.utilites.DBQueries;

@Repository
public class GameDaoImpl implements GameDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addGame(Game game) {

		String sqlQuery = DBQueries.INSERT_GAME;
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", game.getUserId())
				.addValue("score", game.getScore()).addValue("numOfQuestions", game.getNumOfQuestions())
				.addValue("playedAt", LocalDateTime.now())
				.addValue("amount", game.getAmount());
		jdbc.update(sqlQuery, params);

	}

	@Override
	public Integer getTotalNumberOfGamesOfUserId(int userId) {
		String sqlQuery = DBQueries.GET_TOTAL_GAMES_OF_USER;

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);

		return jdbc.queryForObject(sqlQuery, params, Integer.class);

	}

	@Override
	public Double getSumOfScorePercentageByUserId(int userId) {
		String sqlQuery = DBQueries.GET_SUM_OF_SCORE_PERCENTAGE_BY_USER_ID;

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);

		return jdbc.queryForObject(sqlQuery, params, Double.class);

	}

	@Override
	public List<Game> getAllGames(Integer userId) {
		String sqlQuery = DBQueries.GET_ALL_GAMES;
		int userIdFlag = (userId != null) ? 1 : 0;

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId).addValue("userIdFlag",
				userIdFlag);
		List<Game> gamesList = jdbc.query(sqlQuery, params, new GameRowMapper());

		return gamesList;
	}

	@Override
	public List<Map<String, Object>> getLeaderboard() {
		List<Map<String, Object>> res = jdbcTemplate.query(DBQueries.GET_LEADERBOARD, (rs, rowNum) -> {
            Map<String, Object> row = new HashMap<>();
            row.put("userId", rs.getInt("UserId"));
            row.put("username", rs.getString("Username"));
            row.put("score", rs.getDouble("score"));
            return row;
        });
        return res;
	}



}
