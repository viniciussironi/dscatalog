package com.vinicius.dscatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vinicius.dscatalog.entities.User;
import com.vinicius.dscatalog.factories.Factory;
import com.vinicius.dscatalog.projections.UserDetailsProjection;
import com.vinicius.dscatalog.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	@Mock
	private UserRepository repository;
	
	private String existingUserName;
	private String nonExistingUserName;
	private User user;
	private List<UserDetailsProjection> userDetails;
	
	@BeforeEach
	void setUp() throws Exception {
		existingUserName = "joao@gmail.com";
		nonExistingUserName = "naoexiste@gmail.com";
		user = Factory.createAdminUser();
		userDetails = Factory.createUserDetails(existingUserName);

		Mockito.when(repository.searchUserAndRolesByEmail(existingUserName)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUserName)).thenReturn(new ArrayList<>());
	}

	@Test
	public void loadUserByUserNameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(existingUserName);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUserName);
	}

	@Test
	public void loadUserByUserNameShouldThrowUserNotFoundExceptionWhenDoesNotExists() {
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUserName);
		});
	}
}
