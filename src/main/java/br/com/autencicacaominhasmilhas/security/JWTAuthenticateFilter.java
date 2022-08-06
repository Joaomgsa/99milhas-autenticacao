package br.com.autencicacaominhasmilhas.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.Authentication;

public class JWTAuthenticateFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;
	
	public JWTAuthenticateFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager= authenticationManager;
	}
	
	@Override 
	public Authentication attemptAuthentication(httpServletRequest request,
												httpServletResponse response) throws AuthenticationException{
		
	}
	
}
