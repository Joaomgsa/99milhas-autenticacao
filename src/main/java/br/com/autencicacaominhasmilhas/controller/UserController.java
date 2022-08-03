package br.com.autencicacaominhasmilhas.controller;

import br.com.autencicacaominhasmilhas.model.UserModel;
import br.com.autencicacaominhasmilhas.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserRepository repository;
	private final PasswordEncoder encoder;
	
	public UserController(UserRepository repository, PasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<List<UserModel>> listAll() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping("/save")
	public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return ResponseEntity.ok(repository.save(user));
	}
	
	@GetMapping("/validatePassword")
	public ResponseEntity<Boolean> validatePassword(@RequestParam String login,
													@RequestParam String password) {
		
		Optional<UserModel> optUser = repository.findByLogin(login);
		if (optUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
		}
		
		
		
		UserModel user = optUser.get();
		boolean valid = encoder.matches(password, user.getPassword());
		
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		
		return ResponseEntity.status(status).body(valid);
	}
}
