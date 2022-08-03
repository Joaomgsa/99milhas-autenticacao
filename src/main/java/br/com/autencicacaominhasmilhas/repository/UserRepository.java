package br.com.autencicacaominhasmilhas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.autencicacaominhasmilhas.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer>{

	public Optional<UserModel> findByLogin(String login); 
}
