package com.kbc.KbcApp.schedulers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.pojos.Game;
import com.kbc.KbcApp.service.GameService;
import com.kbc.KbcApp.utilites.CreateAndWriteGame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GameScheduler {

    private final GameService gameService;
    private final CreateAndWriteGame createAndWriteGame;

    @Autowired
    public GameScheduler(GameService gameService, CreateAndWriteGame createAndWriteGame) {
        this.gameService = gameService;
        this.createAndWriteGame = createAndWriteGame;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void gameData() {
        List<Game> games = gameService.getAllGames(null); 
        if (games == null || games.isEmpty()) {
    		log.info("Game Excel Export is not Successful..");
        } else {
            createAndWriteGame.createAndWriteGameExcel(games);
    		log.info("Game Excel Export Successful..");
        }
    }
    
    
}