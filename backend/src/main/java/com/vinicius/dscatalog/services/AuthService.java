package com.vinicius.dscatalog.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.vinicius.dscatalog.dtos.EmailDTO;
import com.vinicius.dscatalog.dtos.NewPasswordDTO;
import com.vinicius.dscatalog.entities.PasswordRecover;
import com.vinicius.dscatalog.entities.User;
import com.vinicius.dscatalog.repositories.PasswordRecoverRepository;
import com.vinicius.dscatalog.repositories.UserRepository;
import com.vinicius.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
	
	@Value("${email.password-recover.token.minutes}") 
	private Long tokenMinutes;
	
	@Value("${email.password-recover.uri}") 
	private String recoverUri;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository  userRepository;
	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;
	
	
	@Autowired
	private EmailService emailService;
	
	@Transactional
	public void createRecoverToken(EmailDTO dto) {
		User user = userRepository.findByEmail(dto.getEmail());
		if(user == null) {
			throw new ResourceNotFoundException("E-mail não encontrado");
		}	
		PasswordRecover entity = new PasswordRecover();
		entity.setEmail(dto.getEmail());
		entity.setToken(UUID.randomUUID().toString());
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		
		passwordRecoverRepository.save(entity);
		String body = "Acesse o link para redefinir sua senha\n\n"
				+ recoverUri + entity.getToken() + "\nValidade de " + tokenMinutes + " minutos!";
		
		emailService.sendEmail(dto.getEmail(), "Recuperção de senha", body);
	}
	
	@Transactional
	public void saveNewPassword(NewPasswordDTO dto) {
		List<PasswordRecover> list = passwordRecoverRepository.searchValidTokens(dto.getToken(), Instant.now());
		if(list.size() == 0) {
			throw new ResourceNotFoundException("Token não encontrado");
		}
		
		User user = userRepository.findByEmail(list.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		user = userRepository.save(user);
	}
	
	protected User authenticated() {
		  try {
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
		    String username = jwtPrincipal.getClaim("username");
		    return userRepository.findByEmail(username);
		  }
		  catch (Exception e) {
		    throw new UsernameNotFoundException("Invalid user");
		  }
		}


}
