package com.kbc.KbcApp.utilites;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.service.GameService;

@Component
public class CreateAndWriteLeaderboard{

	@Autowired
	private GameService gameService;
	public void createExcel() throws IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("User's Leaderboard");
		sheet.createRow(0);
		sheet.getRow(0).createCell(0).setCellValue("Rank");	
		sheet.getRow(0).createCell(1).setCellValue("UserId");	
		sheet.getRow(0).createCell(2).setCellValue("UserName");	
		sheet.getRow(0).createCell(3).setCellValue("Score");	
		List<Map<String, Object>> allUsers = gameService.getLeaderboard();
		for (int i = 0; i < allUsers.size(); i++) {
		    XSSFRow row = sheet.createRow(i + 1);
		    Map<String, Object> user = allUsers.get(i);

		    int userId = (int) user.get("userId");
		    String username = (String) user.get("username");

		    row.createCell(0).setCellValue(i + 1);            
		    row.createCell(1).setCellValue(userId);              
		    row.createCell(2).setCellValue(username);           

		    Double avgScore = gameService.getAvgScorePercentageByUserId(userId);
		    row.createCell(3).setCellValue(avgScore);            
		}

		File file = new File("./Excels/UsersLeaderboard.xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		workbook.close();	
	}

}