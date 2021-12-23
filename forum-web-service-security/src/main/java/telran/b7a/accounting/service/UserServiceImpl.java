package telran.b7a.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.UserRepository;
import telran.b7a.accounting.dto.NewUserDto;
import telran.b7a.accounting.dto.UpdateRoleDto;
import telran.b7a.accounting.dto.UpdateUserDto;
import telran.b7a.accounting.dto.UserAccountDto;
import telran.b7a.accounting.dto.exceptions.UserExistsException;
import telran.b7a.accounting.dto.exceptions.UserNotFoundException;
import telran.b7a.accounting.model.UserAccount;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	ModelMapper modelMapper;
	PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserAccountDto addUser(NewUserDto newUserDto) {
		if (userRepository.existsById(newUserDto.getLogin())) {
			throw new UserExistsException(newUserDto.getLogin());
		}
		UserAccount user = modelMapper.map(newUserDto, UserAccount.class);
		user.addRole("USER".toUpperCase());
		String password = passwordEncoder.encode(newUserDto.getPassword());
		user.setPassword(password);
		userRepository.save(user);
		return modelMapper.map(user, UserAccountDto.class);
	}

//	@Override
//	public UserAccountDto loginUser(String login) {
//		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
//		
//		return modelMapper.map(user, UserAccountDto.class);
//	}
	@Override
	public UserAccountDto getUser(String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		return modelMapper.map(user, UserAccountDto.class);
	}

	@Override
	public UserAccountDto removeUser(String login) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		userRepository.delete(user);
		return modelMapper.map(user, UserAccountDto.class);
	}

	@Override
	public UserAccountDto editUser(String login, UpdateUserDto updateUserDto) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		String firstNameEdited = updateUserDto.getFirstName();
		if (firstNameEdited != null) {
			user.setFirstName(firstNameEdited);
		}
		String lastNameEdited = updateUserDto.getLastName();
		if (lastNameEdited != null) {
			user.setLastName(lastNameEdited);
		}
		userRepository.save(user);
		return modelMapper.map(user, UserAccountDto.class);
	}

	@Override
	public UpdateRoleDto changeRolesList(String login, String role, boolean isAddRole) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (isAddRole) {
			user.addRole(role);
		} else {
			user.removeRole(role);
		}
		userRepository.save(user);
		return modelMapper.map(user, UpdateRoleDto.class);
	}

	@Override
	public void changePassword(String login, String password) {
		UserAccount user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);

	}

}
