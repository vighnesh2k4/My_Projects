package com.kbc.KbcApp.utilites;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.pojos.GameConfiguration;
import com.kbc.KbcApp.service.GameConfigurationService;

@Component
public class CreateAndWriteGameConfiguration {
	
	@Autowired
	private GameConfigurationService config;
	
	public void createExcel() throws IOException {
		// TODO Auto-generated method stub
		XSSFWorkbook workBook =new XSSFWorkbook();
		XSSFSheet sheet=workBook.createSheet("Configurations log");
		
		sheet.createRow(0);
		
		sheet.getRow(0).createCell(0).setCellValue("noQuestions");
		sheet.getRow(0).createCell(1).setCellValue("Time Allocated");
		sheet.getRow(0).createCell(2).setCellValue("Modified At");
		sheet.getRow(0).createCell(3).setCellValue("Modified By");
		
		List<GameConfiguration> list=config.getLog();
		for(int i=0;i<list.size();i++)
		{
			XSSFRow row=sheet.createRow(i+1);
			row.createCell(0).setCellValue(list.get(i).getNoQuestions());
			row.createCell(1).setCellValue(list.get(i).getTimeAllocated());
			row.createCell(2).setCellValue(list.get(i).getModifiedAt().toString());
			row.createCell(3).setCellValue(list.get(i).getModifiedBy());
		}
		
		File file=new File("./Excels/ConfigLog.xlsx");
		FileOutputStream fos=new FileOutputStream(file);
		workBook.write(fos);
		workBook.close();
	
	}
	
}