package com.xtrasoft.collegeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollegeServerApplication {

	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(CollegeServerApplication.class, args);
//		var userRepository = (UserRepository) applicationContext.getBean("userRepository");
//		var user = userRepository.findByUserName("admin");
//
//		if(user == null){
//			var passwordEncoder = new BCryptPasswordEncoder();
//			user = new User();
//			var role = new Role();
//			role.setName("ROLE_ADMIN");
//			user.setUserName("admin");
//			user.setRoles(Arrays.asList(role));
//			user.setPassword(passwordEncoder.encode("nimda"));
//		}else{
//			user.setFirstName("Landry");
//		}
//
//		userRepository.save(user);
//		System.out.println(user);


	}



}
