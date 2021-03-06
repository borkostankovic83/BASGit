package com.revature.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	public String HOMEPAGE = "http://ec2-52-90-209-187.compute-1.amazonaws.com:5555/BASGit/static/BASGit/#/topnews/topnews";
	public String PROFILEPAGE = "http://ec2-52-90-209-187.compute-1.amazonaws.com:5555/BASGit/static/BASGit/#/profile/profilepage";
	
	@Autowired
	private UserService userService;

	@PostMapping(value = "/login")
	@ResponseBody
	public ResponseEntity<User> login(@RequestBody String usernameAndPasswordJSON, HttpServletRequest request, HttpServletResponse response) throws IOException{
		User u = userService.login(usernameAndPasswordJSON);
		
		response.sendRedirect(HOMEPAGE);
		if(u != null) {
			request.getSession().setAttribute("currentUser", u);
			return ResponseEntity.status(HttpStatus.OK).body(u);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		
	}
	
	@GetMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<User> findOne(@PathVariable("id") int id) {

		User u =  userService.findOne(id);

		if (u != null) {
			return ResponseEntity.status(HttpStatus.OK).body(u);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@GetMapping(value = "/name/{username}")
	@ResponseBody
	public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
		
		User u = userService.findByUsername(username);
		
		if(u != null) {
			return ResponseEntity.status(HttpStatus.OK).body(u);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<User>> findAll(){
		
		List<User> u = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(u);
	}
	
	@PutMapping
	@ResponseBody
	public ResponseEntity<User> save(User user){
		userService.save(user);
		
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping(value = "/signout")
	@ResponseBody
	public ResponseEntity signOut(User user, HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.getSession().setAttribute("currentUser", null);
		response.sendRedirect(HOMEPAGE);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping(value = "/profile/update")
	@ResponseBody
	public ResponseEntity<User> update(@RequestBody String JSONString, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		User user = userService.updateUser(JSONString, request);
		response.sendRedirect(PROFILEPAGE);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@PostMapping(value = "/signup")
	@ResponseBody
	public ResponseEntity<User> signUp(@RequestBody String JSONString, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		User u = userService.signUp(JSONString);
		u = userService.save(u);
		request.getSession().setAttribute("currentUser", u);
		response.sendRedirect(HOMEPAGE);
		return ResponseEntity.status(HttpStatus.OK).body(u);
		
	}
	
	@GetMapping(value = "/current")
	@ResponseBody
	public ResponseEntity<User> currentUser(HttpServletRequest request){
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		
		if (currentUser != null) {
			return ResponseEntity.status(HttpStatus.OK).body(currentUser);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body(new User(0, "", "", "", false, "", null));
		}

		}
	

}
