package com.kbc.KbcApp.utilites;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.kbc.KbcApp.pojos.Users;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateAndWriteUsers {
	public String excelExport(List<Users> users) throws IOException {
		Workbook workbook=new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Users");
		
		Row headerRow = sheet.createRow(0);
		String[] headers = {"UserId", "Username", "Password","Role", "Status",
				"CreatedAt","ModifiedAt","ModifiedBy"};
		for(int i=0; i<headers.length;i++) {
			Cell cell =headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}
		
		int rowNum=1;
		for(Users user: users) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(user.getUserId());
			row.createCell(1).setCellValue(user.getUsername());
			row.createCell(2).setCellValue(user.getPassword());
			row.createCell(3).setCellValue(user.getRole().name());
			row.createCell(4).setCellValue(user.getStatus().name());
			row.createCell(5).setCellValue(user.getCreatedAt());
			row.createCell(6).setCellValue(user.getModifiedAt());
			row.createCell(7).setCellValue(user.getModifiedBy());
		}
		
		for(int i=0; i<headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		String filePath="./Excels/users_export.xlsx";
		try(FileOutputStream fileOut = new FileOutputStream(filePath)){
			workbook.write(fileOut);
		}
		finally {
			workbook.close();
		}
		return filePath;
	}
}