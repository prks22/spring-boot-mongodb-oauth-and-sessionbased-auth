package com.demo.common.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
@Configuration
public class WebSecurityCorsFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
    	((HttpServletResponse) res).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) res).addHeader("Access-Control-Allow-Methods", "GET, PATCH, OPTIONS, DELETE, HEAD, PUT, POST");
		((HttpServletResponse) res).addHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Content-Type, X-Codingpedia, Authorization,x-auth-token");
		((HttpServletResponse) res).addHeader("Access-Control-Allow-Credentials", "x-auth-token");
		chain.doFilter(req, res);
		
    }
}