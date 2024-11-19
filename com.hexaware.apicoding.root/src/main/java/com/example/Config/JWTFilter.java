package com.example.Config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Service.JWTService;
import com.example.Service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	JWTService jwtService;
	
	@Autowired
	ApplicationContext context;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeaderString = request.getHeader("Authorization");
		String tokenString = null;
		String usernameString = null;
		
		if(authHeaderString!=null && authHeaderString.startsWith("Bearer"))
		{
			tokenString = authHeaderString.substring(7);
			usernameString = jwtService.extractUsername(tokenString);
		}
		
		if(usernameString!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(usernameString);
			if(jwtService.validateToken(tokenString,userDetails))
			{
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
