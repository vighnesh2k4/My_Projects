package com.kbc.KbcApp.schedulers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.utilites.CreateAndWriteQuestions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QuestionsScheduler {

	@Autowired
	private CreateAndWriteQuestions createAndWriteQuestionsExcel;
	

    @Scheduled(cron = "0 */5 * * * *")
	public void InsertQuestionsToExcel() throws IOException {
		createAndWriteQuestionsExcel.createExcel();
		log.info("Questions Excel Export Successful..");
	}
}
