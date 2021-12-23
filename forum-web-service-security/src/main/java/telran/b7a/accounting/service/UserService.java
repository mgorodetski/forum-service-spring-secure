package telran.b7a.accounting.service;

import telran.b7a.accounting.dto.NewUserDto;
import telran.b7a.accounting.dto.UpdateRoleDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.dto.UserAccountDto;

public interface UserService {
	UserAccountDto addUser(NewUserDto newUserDto);

	UserAccountDto getUser(String login);

	UserAccountDto removeUser(String login);

	UserAccountDto editUser(String login, UpdateUserDto updateUserDto);

	UpdateRoleDto changeRolesList(String login, String role, boolean isAddRole);

	void changePassword(String login, String password);

	//UserAccountDto loginUser(String login);

}
