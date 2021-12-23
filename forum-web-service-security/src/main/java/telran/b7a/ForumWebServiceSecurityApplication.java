package telran.b7a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.b7a.accounting.dao.UserRepository;
import telran.b7a.accounting.model.UserAccount;

@SpringBootApplication
public class ForumWebServiceSecurityApplication implements CommandLineRunner {
	@Autowired
	UserRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ForumWebServiceSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin")) {
			// String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			String password = passwordEncoder.encode("admin");
			UserAccount adminAccount = new UserAccount("admin", password, "", "");
			adminAccount.addRole("USER".toUpperCase());
			adminAccount.addRole("MODERATOR".toUpperCase());
			adminAccount.addRole("ADMINISTRATOR".toUpperCase());
			repository.save(adminAccount);
		}

	}

}
