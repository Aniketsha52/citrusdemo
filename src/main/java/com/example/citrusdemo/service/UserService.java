package com.example.citrusdemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.citrusdemo.beans.User;
import com.example.citrusdemo.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public User saveUser(User user) {
		User newUser = userDAO.save(user);
		return newUser;
	}

	public User findUser(Long id) {
		Optional<User> optionalUser = userDAO.findById(id);

		if (!optionalUser.isPresent()) {
			throw new RuntimeException("User Not Found" + id);
		}
		return optionalUser.get();
	}
}
