package com.kbc.KbcApp.schedulers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.utilites.CreateAndWriteGameConfiguration;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class GameConfigurationsScheduler {
	
	
	@Autowired
	private CreateAndWriteGameConfiguration writeExcel;
	
	@Scheduled(cron="0 */5 * * * *")
	public void InsertLogsToExcel() throws IOException
	{
		writeExcel.createExcel();
		log.info("Game Configuration Excel Export Successful..");

	}
}