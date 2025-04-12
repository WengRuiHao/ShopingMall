package auth_service.demo;

import auth_service.repository.UserRepository;
import auth_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
//		UserDto userDto=new UserDto("翁瑞豪","0325","翁","男","admin");
		System.out.println(userService.findByUser("翁瑞豪").toString());
	}

}
