
package com.kbc.KbcApp.utilites;

import com.kbc.KbcApp.pojos.Game;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CreateAndWriteGame {

	public void createAndWriteGameExcel(List<Game> games) {
		File file = new File("game_data.xlsx");
		boolean fileExists = file.exists();

		try (Workbook workbook = fileExists ? new XSSFWorkbook(new FileInputStream(file)) : new XSSFWorkbook()) {
			Sheet sheet;

			if (workbook.getNumberOfSheets() == 0) {
				sheet = workbook.createSheet("Games");
			} else {
				sheet = workbook.getSheetAt(0);
			}

			if (sheet.getPhysicalNumberOfRows() == 0) {
				Row header = sheet.createRow(0);
				header.createCell(0).setCellValue("Game ID");
				header.createCell(1).setCellValue("User ID");
				header.createCell(2).setCellValue("Score");
				header.createCell(3).setCellValue("Number of Questions");
				header.createCell(4).setCellValue("Played At");
				header.createCell(5).setCellValue("Amount won");
			}

			int currentRows = sheet.getPhysicalNumberOfRows();
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			

			for (Game game : games) {
				if (game.getGameId() >= currentRows) {
					Row row = sheet.createRow(currentRows++);
					row.createCell(0).setCellValue(game.getGameId());
					row.createCell(1).setCellValue(game.getUserId());
					row.createCell(2).setCellValue(game.getScore());
					row.createCell(3).setCellValue(game.getNumOfQuestions());
					row.createCell(4).setCellValue(game.getPlayedAt().toString());
					row.createCell(5).setCellValue(game.getAmount());
				}
			}

			try (FileOutputStream fileOut = new FileOutputStream("game_data.xlsx")) {
				workbook.write(fileOut);
				System.out.println("Games appended to Excel successfully!");
			} catch (IOException e) {
				e.getMessage();
				System.out.println("Error while creating or appending to excel file.");
			}
		} catch (IOException e) {
			e.getMessage();
			System.out.println("Error while creating or appending to Excel file.");
		}
	}
}
