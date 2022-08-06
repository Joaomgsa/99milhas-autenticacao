package br.com.autencicacaominhasmilhas.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.autencicacaominhasmilhas.data.UserDetailsData;
import br.com.autencicacaominhasmilhas.model.UserModel;
import br.com.autencicacaominhasmilhas.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final UserRepository repository;
	
	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserModel> user = repository.findByLogin(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Usuario [" + username +"] não encontrado");
		}
		return new UserDetailsData(user);
	}

}
