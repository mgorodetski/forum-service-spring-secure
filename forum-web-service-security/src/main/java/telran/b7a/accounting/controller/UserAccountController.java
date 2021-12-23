package telran.b7a.accounting.controller;

import java.security.Principal;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.accounting.dto.LoginUserDto;
import telran.b7a.accounting.dto.NewUserDto;
import telran.b7a.accounting.dto.UpdateRoleDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.dto.UserAccountDto;
import telran.b7a.accounting.service.UserService;

@RestController
@RequestMapping("/account")
public class UserAccountController {
	UserService userService;

	@Autowired
	public UserAccountController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public UserAccountDto registerUser(@RequestBody NewUserDto newUserDto) {
		return userService.addUser(newUserDto);
	}

//	@PostMapping("/login")
//	public UserAccountDto loginUser(@RequestHeader("Authorization") String token) { //, @RequestBody LoginUserDto loginUserDto) {
//		//String[] arr = token.split(" ");
//		token = token.split(" ")[1];
//		byte[] bytesDecode = Base64.getDecoder().decode(token); 
//		token = new String(bytesDecode);
//		String[] credentials = token.split(":");
//		return userService.getUser(credentials[0]);
//	}
	
	@PostMapping("/login")
	public UserAccountDto loginUser(Principal principal) { 
		return userService.getUser(principal.getName());
	}

	@DeleteMapping("/user/{login}")
	public UserAccountDto deleteUser(@PathVariable String login) {
		return userService.removeUser(login);
	}

	@PutMapping("/user/{login}")
	public UserAccountDto updateUser(@PathVariable String login, @RequestBody UpdateUserDto updateUserDto) {
		return userService.editUser(login, updateUserDto);
	}

	@PutMapping("/user/{login}/role/{role}")
	public UpdateRoleDto addRole(@PathVariable String login, @RequestBody String role) {
		return userService.changeRolesList(login, role, true);

	}

	@DeleteMapping("/user/{login}/role/{role}")
	public UpdateRoleDto deleteRole(@PathVariable String login, @RequestBody String role) {
		return userService.changeRolesList(login, role, false);
	}
	
	@PutMapping("/user/password")
	public void changePassword(@RequestBody LoginUserDto userDto) {
		userService.changePassword(userDto.getLogin(), userDto.getPassword());
	}
}
