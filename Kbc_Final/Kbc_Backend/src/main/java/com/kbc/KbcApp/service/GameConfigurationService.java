package com.kbc.KbcApp.service;

import java.util.List;

import com.kbc.KbcApp.pojos.GameConfiguration;
import com.kbc.KbcApp.pojos.NewConfig;

public interface GameConfigurationService {
	void setNumberOfQuestionsAndTimer(int questions, int seconds,  
			String modifiedBy) throws Exception;
	NewConfig getNumberOfQuestionsAndTimer() throws Exception;
	GameConfiguration getGameConfigurations() throws Exception;	
	public List<GameConfiguration> getLog();
}