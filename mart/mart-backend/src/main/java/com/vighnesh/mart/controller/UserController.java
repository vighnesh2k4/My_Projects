package com.vighnesh.mart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vighnesh.mart.handler.MartException;
import com.vighnesh.mart.pojo.ResponseObject;
import com.vighnesh.mart.pojo.User;
import com.vighnesh.mart.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseObject createUser(@RequestBody User user) throws MartException {
		userService.addUser(user);
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "User created successfully");
		return responseObject;
	}
	
	@PostMapping("/login")
    public ResponseObject login(@RequestBody User authRequest) throws Exception {
        User user = userService.getUserAuth(authRequest);
        if (user==null) {
            throw new Exception("Invalid credentials");
        }
        System.out.println(1);
        System.out.println(user.getStatus().equals(User.Status.INACTIVE));
        if(user.getStatus().equals(User.Status.INACTIVE)) {
        	throw new Exception("User Deleted, Create New Account");
        }
        ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, user, "User Login successfully");
		return responseObject; 
    }
	
	@GetMapping("/getUser/{userId}")
	public ResponseObject getUser(@PathVariable int userId) throws MartException {
		User temp = new User();
		temp.setUser_id(userId);
		User user = userService.getUser(temp);
		if(user==null) {
			throw new MartException("User not found with ID: " + userId);
		}
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, user, "User data retrieved successfully");
		return responseObject;
	}
	
	@PutMapping("/updateUser/{userId}")
	public ResponseObject updateUser(@PathVariable int userId, @RequestBody User updateRequest) throws MartException{
		updateRequest.setUser_id(userId);
	 	userService.updateUser(updateRequest);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "User data updated successfully");
		return responseObject;
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseObject deleteUser(@PathVariable int userId) throws MartException{
		User delUser = new User();
		delUser.setUser_id(userId);
	 	userService.deleteUser(delUser);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "User deleted successfully");
		return responseObject;
	}
	
	@GetMapping("/getAllUsers")
	public ResponseObject getAllUsers() throws MartException{
		
		ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, userService.getAllUsers(), "Users retrieved successfully");
		return responseObject;
	}
	
	@PutMapping("/updateUserRole/{userId}")
	public ResponseObject updateUserRole(@PathVariable int userId, @RequestBody User updateRequest) throws MartException{
		updateRequest.setUser_id(userId);	 
	 	userService.updateUserRole(updateRequest);
	 	ResponseObject responseObject = new ResponseObject(ResponseObject.Status.SUCCESS, null, "User role updated successfully");
		return responseObject;
	}	
	
}
