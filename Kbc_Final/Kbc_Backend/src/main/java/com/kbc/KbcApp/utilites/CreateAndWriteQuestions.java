package com.kbc.KbcApp.utilites;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.pojos.Question;
import com.kbc.KbcApp.service.QuestionService;

@Component
public class CreateAndWriteQuestions {
	@Autowired
	private QuestionService questionService;
	
	public void createExcel() throws IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("Questions");
		CellStyle cellStyle = workBook.createCellStyle();
	    cellStyle.setAlignment(HorizontalAlignment.CENTER); 
		sheet.createRow(0);
		String[] columns = {"QuestionId", "Question", "Answer","CreatedBy", "CreatedAt","ModifiedAt","ModifiedBy"};
		for(int i=0; i<columns.length;i++) {
			XSSFCell cell=sheet.getRow(0).createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(cellStyle);
		}
		
		List<Question> allQuestions = questionService.showAllQuestions(null, null, null);
		for(int i=0;i<allQuestions.size();i++) {
			XSSFRow row=sheet.createRow(i+1);
			XSSFCell cell1=row.createCell(0);
			cell1.setCellValue(allQuestions.get(i).getQuestionId());
			cell1.setCellStyle(cellStyle);

			XSSFCell cell2=row.createCell(1);
			cell2.setCellValue(allQuestions.get(i).getQuestion());
			cell2.setCellStyle(cellStyle);
			
			XSSFCell cell3=row.createCell(2);
			cell3.setCellValue(allQuestions.get(i).getOption1());
			cell3.setCellStyle(cellStyle);
			
			XSSFCell cell4=row.createCell(3);
			cell4.setCellValue(allQuestions.get(i).getCreatedBy());
			cell4.setCellStyle(cellStyle);

			XSSFCell cell5=row.createCell(4);
			cell5.setCellValue(allQuestions.get(i).getCreatedAt());
			cell5.setCellStyle(cellStyle);
			
			XSSFCell cell6=row.createCell(5);
			cell6.setCellValue(allQuestions.get(i).getModifiedBy());
			cell6.setCellStyle(cellStyle);

			XSSFCell cell7=row.createCell(6);
			cell7.setCellValue(allQuestions.get(i).getModifiedAt());
			cell7.setCellStyle(cellStyle);
		}
		File file = new File("./Excels/Questions.xlsx");
		FileOutputStream fos= new FileOutputStream(file);
		workBook.write(fos);
		workBook.close();
	}
}
