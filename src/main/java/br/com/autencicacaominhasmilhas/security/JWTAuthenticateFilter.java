package br.com.autencicacaominhasmilhas.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.autencicacaominhasmilhas.data.UserDetailsData;
import br.com.autencicacaominhasmilhas.model.UserModel;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter{
	
	public static final int TOKEN_EXPIRACAO = 600_000;
	public static final String TOKEN_SENHA = "506bfc31-45cd-4086-bcc9-7511cdab7a22";
	
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
			
			throw new RuntimeException("Falha ao Autenticar o usuário",e);
		}
	}
	
	@Override
	public void successfulAuthentication(HttpServletRequest request,
										 HttpServletResponse response,
										 FilterChain chain,
										 Authentication authResult) throws IOException,ServletException {
		
		UserDetailsData userData = (UserDetailsData) authResult.getPrincipal();
		
		String token = JWT.create().
				withSubject(userData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
				.sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.getWriter().write(token);
		response.getWriter().flush();
	}
	
}
