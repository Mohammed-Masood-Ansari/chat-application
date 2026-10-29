package com.mazhar.chat_application.service;

import com.mazhar.chat_application.dto.request.LoginRequest;
import com.mazhar.chat_application.dto.request.RegisterRequest;
import com.mazhar.chat_application.dto.response.AuthResponse;
import com.mazhar.chat_application.entity.User;
import com.mazhar.chat_application.repository.UserRepository;
import com.mazhar.chat_application.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public String register(RegisterRequest registerRequest) {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			throw new RuntimeException("Username is already taken!");
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new RuntimeException("Email is already registered!");
		}

		User user = User.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.avatarUrl(registerRequest.getAvatarUrl()).build();

		userRepository.save(user);

		return "User registered successfully! Please login to continue.";
	}

	@Override
	public AuthResponse login(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		User user = userRepository.findByUsername(loginRequest.getUsernameOrEmail())
				.or(() -> userRepository.findByEmail(loginRequest.getUsernameOrEmail()))
				.orElseThrow(() -> new RuntimeException("User not found"));

		return AuthResponse.builder().token(token).type("Bearer").id(user.getId()).username(user.getUsername())
				.email(user.getEmail()).build();
	}
}