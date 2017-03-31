package com.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unchecked")
public class ApiController {
	
	@RequestMapping(value = "api/role/{role}", method = RequestMethod.GET)
	public Boolean login(@PathVariable final String role, final HttpServletRequest request) throws ServletException {
		final Claims claims = (Claims) request.getAttribute("claims");
		boolean contains = ((List<String>) claims.get("roles")).contains(role);
		return contains;
	}
	
	private final HashMap<String, Object> userDb = new HashMap<String, Object>();

	public ApiController() {
		userDb.put("tom", Arrays.asList("user"));
		userDb.put("sally", Arrays.asList("user", "admin"));
	}

	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public Object login(@RequestBody final UserLogin login) throws Exception {
		if (login.name == null || !userDb.containsKey(login.name)) {
			throw new Exception("Invalid login");
		}
		String compact = Jwts.builder().setSubject(login.name).claim("roles", userDb.get(login.name))
		.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		return new LoginResponse(compact);
	}

	private static class UserLogin {
		public String name;
//		public String password;
	}

	public class LoginResponse {
		public String token;
		public LoginResponse(final String token) {
			this.token = token;
		}
	}
}
