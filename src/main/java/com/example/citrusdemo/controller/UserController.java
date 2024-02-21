package com.example.citrusdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.citrusdemo.beans.User;
import com.example.citrusdemo.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		User newUser = userService.saveUser(user);
		return ResponseEntity.ok().body("User saved successfully with name : " + newUser.getUserName());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
		User newUser = userService.findUser(id);
		return ResponseEntity.ok().body(newUser);
	}

}
