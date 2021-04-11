package com.user_api.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user_api.main.entities.User;
import com.user_api.main.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	private final static String MY_LOG= "My logger: ";
	
	public List<User> getFilteredUserList(String field, String filter) {
		try {

			return userRepository.getUserByFilter(field, filter);			
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return null;
		}
	}
	
	public String addUserList(List<User> userList) {
		try {

			if(userRepository.addUserList(userList))
				return "All records inserted";
			
			return "Unalbe to insert records.";
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return "Error in inserting record: "+ exception.toString();
		}		
	}
	
	public String updateUser(User user) {
		try {

			return "User with ID: "+ userRepository.updateUser(user).getUserId()+ " updated";			
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return "Error in updating user: "+ exception.toString();
		}
	}
	
	public boolean deleteUser(String userId) {
		try {

			return userRepository.deleteUser(userId);			
		}catch(Exception exception) {
			System.out.println(MY_LOG+ exception.toString());
			return false;
		}
	}
	
}