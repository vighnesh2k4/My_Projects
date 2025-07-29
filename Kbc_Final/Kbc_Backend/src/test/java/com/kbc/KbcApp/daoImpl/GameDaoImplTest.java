package com.kbc.KbcApp.daoImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.kbc.KbcApp.pojos.Game;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class GameDaoImplTest {

	@Autowired
	private GameDaoImpl gameDao;

	@Before
	@Test
	public void testAddGame() throws Exception {

		Game game1 = new Game();
		game1.setUserId(10);
		game1.setScore(6);
		game1.setNumOfQuestions(10);
		game1.setPlayedAt(LocalDateTime.now());

		Game game2 = new Game();
		game2.setUserId(10);
		game2.setScore(8);
		game2.setNumOfQuestions(10);
		game2.setPlayedAt(LocalDateTime.now());

		gameDao.addGame(game1);
		gameDao.addGame(game2);

		int totalGames = gameDao.getTotalNumberOfGamesOfUserId(10);
		assertTrue("Game added successfully", totalGames > 0);
	}
	
	@Test
	public void testGetTotalNumberOfGamesOfUserId() throws Exception {
		
		int totalGames = gameDao.getTotalNumberOfGamesOfUserId(10);
		System.out.println(totalGames);
		assertTrue("Total games should be > 0", totalGames > 0);
		
		Integer actualTotalNumberOfGamesOfUserId = gameDao.getTotalNumberOfGamesOfUserId(10);
		Integer expectedTotalNumberOfGamesOfUserId = 2;
		assertEquals(expectedTotalNumberOfGamesOfUserId, actualTotalNumberOfGamesOfUserId);
	}
	
	@Test
	public void testGetSumOfScorePercentageByUserId() throws Exception {
		
		Double totalSumOfScorePercentage = gameDao.getSumOfScorePercentageByUserId(10);
		assertNotNull("Total score percentage should not be null", totalSumOfScorePercentage);
		
		assertTrue("Total score percentage should be >=0", totalSumOfScorePercentage >= 0);
		
		Double actualSumOfScorePercentageByUserId = gameDao.getSumOfScorePercentageByUserId(10);
		Double expectedSumOfScorePercentageByUserId = 140.0;
		assertEquals(expectedSumOfScorePercentageByUserId, actualSumOfScorePercentageByUserId);

	}
	
	@Test
	public void testGetAllGames() throws Exception {
		
		List<Game> games = gameDao.getAllGames(10);
		assertNotNull("Games list should not be null", games);
		
		assertTrue("Games list should not be empty", !games.isEmpty());
	}

}