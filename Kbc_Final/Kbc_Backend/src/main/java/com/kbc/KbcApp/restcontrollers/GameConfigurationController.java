package com.kbc.KbcApp.restcontrollers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbc.KbcApp.pojos.GameConfiguration;
import com.kbc.KbcApp.pojos.NewConfig;
import com.kbc.KbcApp.pojos.ResponseObject;
import com.kbc.KbcApp.serviceImpl.GameConfigurationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/game-config")
public class GameConfigurationController {
	
	private final GameConfigurationServiceImpl gameConfigService ;
	
	@Autowired
	public GameConfigurationController(GameConfigurationServiceImpl g) {
		this.gameConfigService = g;	
	}
	
	@PutMapping("/set-questions-and-timer")
	public ResponseObject setNumberOfQuestionsAndTimer(@RequestBody NewConfig config,HttpSession session) throws Exception{
		String modifiedBy=(String) session.getAttribute("userName");
		int questions=config.getNoQuestions();
		int seconds=config.getTimeAllocated();
		log.info("setNumberOfQuestionsAndTimer trying to set by {}: questions={}, seconds={}", modifiedBy, questions, seconds);
		gameConfigService.setNumberOfQuestionsAndTimer(questions,seconds,modifiedBy);
		log.info("Number of questions and timer updated successfully by {} to questions={}, seconds={}",modifiedBy,questions,seconds);
		return new ResponseObject("Number of questions and timer updated successfully",null,ResponseObject.Status.SUCCESS);
	}
	

	@GetMapping("/get-questions-and-timer") 
	public ResponseObject getNumberOfQuestionsAndTimer(HttpSession session) throws Exception{
		log.info("getNumberOfQuestionsAndTimer called");
		NewConfig gameConfiguration = gameConfigService.getNumberOfQuestionsAndTimer();
		log.info("Fetched game configuration: {} by {} ", gameConfiguration,session.getAttribute("userName"));
		return new ResponseObject("ok",gameConfiguration,ResponseObject.Status.SUCCESS);
	}
	
	@GetMapping("/get-game-configuration")
	public ResponseObject getGameConfiguration(HttpSession session)  throws Exception{
		log.info("getGameConfiguration called");
		GameConfiguration gameConfig = gameConfigService.getGameConfigurations();
		log.info("Fetched game configuration: {} by {}", gameConfig,session.getAttribute("userName"));
		return new ResponseObject("ok",gameConfig,ResponseObject.Status.SUCCESS);
	}
}