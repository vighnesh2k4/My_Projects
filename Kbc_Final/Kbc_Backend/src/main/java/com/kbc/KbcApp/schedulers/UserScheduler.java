package com.kbc.KbcApp.schedulers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kbc.KbcApp.pojos.Users;
import com.kbc.KbcApp.service.UserService;
import com.kbc.KbcApp.utilites.CreateAndWriteUsers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserScheduler {
	private final UserService userService;
	private final CreateAndWriteUsers excelService;
	
	@Autowired
	public UserScheduler(UserService userService, CreateAndWriteUsers excelService) {
		this.userService = userService;
		this.excelService=excelService;
	}
	
    @Scheduled(cron = "0 */5 * * * *")
	public void scheduler(){
		try {
			List<Users> users = userService.getAllUsers();
			if (users.size()>0){
				String filePath=excelService.excelExport(users);
				log.info("User Excel Export Successful.. Path: "+filePath);
			}else{
				log.warn("No data found");
				return;
			}
		}catch(Exception ex) {
			log.error("Error: "+ ex.getMessage());
		}
	}
}