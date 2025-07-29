package com.kbc.KbcApp.restcontrollers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbc.KbcApp.enums.RoleEnum;
import com.kbc.KbcApp.enums.StatusEnum;
import com.kbc.KbcApp.handler.KbcException;
import com.kbc.KbcApp.pojos.ResponseObject;
import com.kbc.KbcApp.pojos.Users;
import com.kbc.KbcApp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/login")
	public ResponseObject login(@RequestBody @Valid Users u, HttpSession session) throws KbcException {
		Optional<Users> user = userService.validLogin(u.getUsername(), u.getPassword());
		if (!user.isPresent()) {
			log.warn("Login attempt User Controller failed");
			throw new KbcException("Invalid credentials"); 
		}
		
		session.setAttribute("userId", user.get().getUserId());
		session.setAttribute("userName", user.get().getUsername());
		session.setAttribute("userRole", user.get().getRole().name());
		session.setAttribute("userStatus", user.get().getStatus().name());
		session.setAttribute("password", user.get().getPassword());
		
		log.info("Login User Controller Successful");
		ResponseObject responseObject = new ResponseObject("Login successful", user.get(), ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@PostMapping("/logout")
	public ResponseObject logout(HttpSession session) {
		session.invalidate();
		log.info("Logout User Controller Successful");
		ResponseObject responseObject = new ResponseObject("Logged out successfully", null, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@GetMapping("/check-auth")
	public ResponseObject checkAuth(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null) {
			log.warn("Not authenticated User Controller");
			ResponseObject responseObject = new ResponseObject("Not authenticated", null, ResponseObject.Status.FAILURE);
			return responseObject;
		}
		String status=(String)session.getAttribute("userStatus");
		if(status.equals("INACTIVE")) {
			session.invalidate();
			log.warn("Not authenticated User Controller");
			ResponseObject responseObject = new ResponseObject("Not authenticated", null, ResponseObject.Status.FAILURE);
			return responseObject;
		}
		log.info("check-auth User Controller Successful");
		ResponseObject responseObject = new ResponseObject("Authenticated", "User is authenticated", ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@GetMapping("/user-info")
	public ResponseObject getUserInfo(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		String userRole = (String) session.getAttribute("userRole");
		String password = (String) session.getAttribute("password");
		String userStatus = (String) session.getAttribute("userStatus");

		if (userId == null) {
			log.warn("Not authenticated usr info User Controller");
			ResponseObject responseObject = new ResponseObject("Not authenticated", null, ResponseObject.Status.FAILURE);
			return responseObject;
		}
		
		Users userInfo = new Users();
		userInfo.setUserId(userId);
		userInfo.setUsername(userName);
		userInfo.setRole(RoleEnum.valueOf(userRole));
		userInfo.setPassword(password);
		userInfo.setStatus(StatusEnum.valueOf(userStatus));
		
		log.info("user info User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User info retrieved successfully", userInfo, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@PostMapping("/createUser")
	public ResponseObject createUser(@RequestBody Users user) throws KbcException {
		userService.addUser(user);
		log.info("create usr User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User created successfully", null, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@PostMapping("/register")
	public ResponseObject register(@RequestBody @Valid Users user) throws KbcException {
		user.setRole(RoleEnum.USER);
		user.setStatus(StatusEnum.ACTIVE);
		userService.addUser(user);
		log.info("register usr User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User registered successfully", null, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@GetMapping("/allUsers")
	public ResponseObject getAllUsers() {
		List<Users> users = userService.getAllUsers();
		log.info("view users User Controller Successful");
		ResponseObject responseObject = new ResponseObject("Users retrieved successfully", users, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@GetMapping("/{userId}")
	public ResponseObject getUserById(@PathVariable int userId) throws KbcException {
		Optional<Users> user = userService.getUserById(userId);
		if (!user.isPresent()) {
			log.warn("User not found with ID: " + userId+"User Controller");
			throw new KbcException("User not found with ID: " + userId); 
		}
		log.info("view userByID User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User retrieved successfully", user.get(), ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@GetMapping("/allUserData/{userId}")
	public ResponseObject getUserDataById(@PathVariable int userId) throws KbcException {
		Optional<Users> user = userService.getUserDataById(userId);
		if (!user.isPresent()) {
			log.warn("User not found with ID: " + userId+"User Controller");
			throw new KbcException("User not found with ID: " + userId); 
		}
		log.info("AllUserData User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User data retrieved successfully", user.get(), ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@PutMapping("/makeAdmin/{userId}")
	public ResponseObject updateUserRole(@PathVariable int userId, HttpSession session) throws Exception {
		Optional<Users> user = userService.getUserById(userId);
		if (!user.isPresent()) {
			log.warn("User not found with ID: " + userId+"User Controller");
			throw new KbcException("User not found with ID: " + userId); 
		}
		String modifiedBy=(String) session.getAttribute("userName");
		Users userToUpdate = user.get();
		if(userToUpdate.getRole()== RoleEnum.valueOf("ADMIN")) {
			userToUpdate.setRole(RoleEnum.valueOf("USER"));
		}else {
			userToUpdate.setRole(RoleEnum.valueOf("ADMIN"));
		}
		userService.updateUserRole(userToUpdate, modifiedBy);
		log.info("Change to Admin User Controller Successful");
		ResponseObject responseObject = new ResponseObject("User role updated successfully", null, ResponseObject.Status.SUCCESS);
		return responseObject;
	}
	
	@PatchMapping("/{userId}/status")
	public ResponseObject updateUserStatus(@PathVariable int userId, @RequestBody @Valid Users userstatus, HttpSession session) throws Exception {
		 Optional<Users> user = userService.getUserById(userId);
		 if (!user.isPresent()) {
				log.warn("User not found with ID: " + userId+"User Controller");
				throw new KbcException("User not found with ID: " + userId); 
		 }
		 String modifiedBy = (String) session.getAttribute("userName");
		 Users userToUpdate = user.get();
		 userToUpdate.setStatus(userstatus.getStatus());
		 userService.updateUserStatus(userToUpdate, modifiedBy);
	
		 if (userstatus.getStatus() == StatusEnum.INACTIVE) {
			 session.invalidate();
		 }
		 log.info("Change Status User Controller Successful");
		 ResponseObject responseObject = new ResponseObject("User status updated successfully", null, ResponseObject.Status.SUCCESS);
		 return responseObject;
	}

	@PatchMapping("/{userId}/update")
	public ResponseObject updateUser(
	 	@PathVariable int userId,
	 	@RequestBody Users updateRequest,
	 	HttpSession session) throws Exception {
	 	Optional<Users> userOpt = userService.getUserById(userId);
	 	if (!userOpt.isPresent()) {
			log.warn("User not found with ID: " + userId+"User Controller");
			throw new KbcException("User not found with ID: " + userId); 
	 	}
	 
	 	Users user = userOpt.get();
	 
	 	if (updateRequest.getUsername() != null) {
	 		user.setUsername(updateRequest.getUsername());
	 	}
	 	if (updateRequest.getPassword() != null) {
	 		user.setPassword(updateRequest.getPassword());
	 	}
	 	if (updateRequest.getStatus() != null) {
	 		user.setStatus(updateRequest.getStatus());
	 	}
	 	String modifiedBy=(String) session.getAttribute("userName");

	 	user.setModifiedBy(modifiedBy);
	 
	 	userService.updateUser(user, modifiedBy);
		session.setAttribute("userName", updateRequest.getUsername());
	 	session.setAttribute("password", updateRequest.getPassword());
	 	session.setAttribute("userStatus", updateRequest.getStatus().name());

		log.info("Update usr User Controller Successful");
	 	ResponseObject responseObject = new ResponseObject("User updated successfully", null, ResponseObject.Status.SUCCESS);
	 	return responseObject;
	}
}