package br.com.autencicacaominhasmilhas.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.autencicacaominhasmilhas.model.UserModel;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager= authenticationManager;
	}
	
	@Override 
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException{
		try {
			UserModel user = new ObjectMapper()
					.readValue(request.getInputStream(), UserModel.class);
		 
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				   user.getLogin(),
				   user.getPassword(),
				   new ArrayList<>()
			));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Falha ao Autenticar o usuário",e);
		}
	}
	
}
