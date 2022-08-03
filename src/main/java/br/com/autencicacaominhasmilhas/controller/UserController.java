package br.com.autencicacaominhasmilhas.controller;

import br.com.autencicacaominhasmilhas.model.UserModel;
import br.com.autencicacaominhasmilhas.repository.UserRepository;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserRepository repository;
	
	public UserController(UserRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/listAll")
	public ResponseEntity<List<UserModel>> listAll() {
		
		return ResponseEntity.ok(repository.findAll());
	}
	
	public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
		return ResponseEntity.ok(repository.save(user));
	}
}
