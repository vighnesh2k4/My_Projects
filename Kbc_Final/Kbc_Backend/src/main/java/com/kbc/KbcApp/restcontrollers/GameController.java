package com.kbc.KbcApp.restcontrollers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbc.KbcApp.handler.KbcException;
import com.kbc.KbcApp.pojos.Game;
import com.kbc.KbcApp.pojos.ResponseObject;
import com.kbc.KbcApp.service.GameService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/game")
@Slf4j
public class GameController {
	
	private final GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping("/add")
	public ResponseObject addGame(@Valid @RequestBody Game game, BindingResult bindingResult,HttpSession session) throws KbcException {
		if (bindingResult.hasErrors()) {
			return new ResponseObject("Invalid Fields",null,ResponseObject.Status.FAILURE);
		}
		String username = (String) session.getAttribute("userName");
		int userId=(int) session.getAttribute("userId");
		log.info("addGame called by user: {} with userId: {}", username,userId);

		if (game == null) {
			log.info("addGame failed by user: {} with userId: {} due to null", username,userId);

			return new ResponseObject("Game is null",null,ResponseObject.Status.FAILURE);
		}

		if (game.getNumOfQuestions() < game.getScore()) {
			log.info("Game load failed by user: {} with userId: {} due to invalid score-questions ratio", username,userId);

			return new ResponseObject("Invalid Score-Questions Ratio",null,ResponseObject.Status.FAILURE);
		}
		
		game.setUserId(userId);
		boolean gameAdded = gameService.addGame(game);

		if (gameAdded) {
			log.info("Succsssful game by user: {} with userId: {} with score {}", username,userId,game.getScore());

			return new ResponseObject("Game added successfully.",null,ResponseObject.Status.SUCCESS);
		} else {
			log.info("failed to add game");

			return new ResponseObject("Failed to add game.",null,ResponseObject.Status.FAILURE);
		}
	}

	@GetMapping("/avgScorePercentage/{userId}")
	public ResponseObject getAvgScorePercentageByUserId(@PathVariable int userId,HttpSession session) {

		if (userId < 0) {
			
			return new ResponseObject("Invalid UserId",null,ResponseObject.Status.FAILURE);
		}
		Integer userIdInt = Integer.valueOf(userId);

		if (userIdInt != null) {
			Double result = gameService.getAvgScorePercentageByUserId(userId);
			log.info("Viewing score percentage average played by user: {} with userId: {}", session.getAttribute("userName"),userId);

			return new ResponseObject("ok",result,ResponseObject.Status.SUCCESS);
		} else {
			return new ResponseObject("UserId is null",null,ResponseObject.Status.FAILURE);
		}
	}

	@GetMapping("/viewGames/{userId}")
	public ResponseObject getAllGames(@PathVariable int userId,HttpSession session) {
		
		log.info("");

		if (userId > 0) {
			List<Game> games = gameService.getAllGames(Integer.valueOf(userId));
			log.info("Viewing score percentage average played by user: {} with userId: {}", session.getAttribute("userName"),userId);

			return new ResponseObject("ok",games,ResponseObject.Status.SUCCESS);
		} else {
			return new ResponseObject("UserId is invalid",null,ResponseObject.Status.FAILURE);
		}

	}

	@GetMapping("/viewGames")
	public ResponseObject getAllGames(HttpSession session) {
		List<Game> games = gameService.getAllGames(null);
		log.debug("showing ");
		return new ResponseObject("ok",games,ResponseObject.Status.SUCCESS);
	}
	
	@GetMapping("/leaderboard")
	public ResponseObject getLeaderboard(HttpSession session) {
		log.info("Viewing LeaderBoard of users to : {} with userId: {}", session.getAttribute("userName"),session.getAttribute("userId"));
		return new ResponseObject("ok", gameService.getLeaderboard(),ResponseObject.Status.SUCCESS);
	}

}