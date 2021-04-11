package com.user_api.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.user_api.main.entities.User;
import com.user_api.main.services.UserService;

@RestController
@RequestMapping(value= "/userAPI")
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(method= RequestMethod.GET, value= {"/users/{field}/{filter}"})
	public List<User> getFileredUserList(@PathVariable(value= "field") String field, @PathVariable(value= "filter") String filter){
		return userService.getFilteredUserList(field, filter);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= "/user/{userId}")
	public User getUserById(@PathVariable(value= "userId") String aUserId) {
		return userService.getFilteredUserList("userId", aUserId).get(0);
	}
	
	@RequestMapping(method= RequestMethod.GET, value= {"/users"})
	public List<User> getFileredUserList(){
		return userService.getFilteredUserList(null, null);
	}
	
	@RequestMapping(method= RequestMethod.POST, value= "/users")
	public String addUserList(@RequestBody List<User> userList) {
		return userService.addUserList(userList);
	}
	
	@RequestMapping(method= RequestMethod.PUT, value= "/user")
	public String updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	@RequestMapping(method= RequestMethod.DELETE, value= "/user/{userId}")
	public boolean deleteUser(@PathVariable(value= "userId") String userId) {
		return userService.deleteUser(userId);
	}
	
}