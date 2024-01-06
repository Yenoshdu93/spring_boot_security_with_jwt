package com.sprintsecurity.springjwtdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sprintsecurity.springjwtdemo.modle.Role;
import com.sprintsecurity.springjwtdemo.modle.User;
import com.sprintsecurity.springjwtdemo.repository.UserRepository;

@SpringBootApplication
public class SpringjwtdemoApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringjwtdemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
			User adminUser = userRepository.findByRole(Role.ADMIN);

			if(null == adminUser){
				User user = User.builder()
				.name("Yenosh")
				.email("dulla@gmail.com")
				.password(new BCryptPasswordEncoder().encode("yenosh"))
				.role(Role.ADMIN)
				.build();
				
				userRepository.save(user);
			}
			
	}

}
