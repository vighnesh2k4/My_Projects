package com.kbc.KbcApp.schedulers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.utilites.CreateAndWriteLeaderboard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UsersLeaderboardScheduler{
	
	@Autowired
	private CreateAndWriteLeaderboard createAndWriteExcel;
	
    @Scheduled(cron = "0 */5 * * * *")
	public void InsertUsersToExcel() throws IOException{
		createAndWriteExcel.createExcel();
		log.info("Leader Excel Export Successful..");

	}
}