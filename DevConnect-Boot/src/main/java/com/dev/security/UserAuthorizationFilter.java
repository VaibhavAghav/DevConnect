/*
 * package com.dev.security;
 * 
 * import com.dev.entities.User; import com.dev.security.service.JwtService;
 * import com.dev.serviceImpl.UserServiceImpl; import
 * jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.MediaType; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import java.io.IOException;
 * 
 * @Component public class UserAuthorizationFilter extends OncePerRequestFilter
 * {
 * 
 * @Autowired private JwtService jwtService;
 * 
 * @Autowired private UserServiceImpl userServiceImpl;
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException { String authHeader =
 * request.getHeader("Authorization");
 * 
 * if (authHeader != null && authHeader.startsWith("Bearer ")) { String token =
 * authHeader.substring(7); System.out.println("Token received from client: " +
 * token);
 * 
 * try { String username = jwtService.extractUsername(token);
 * System.out.println("Token after parsing (username extracted): " + username);
 * // proceed with your logic... } catch (Exception ex) { ex.printStackTrace();
 * System.out.println("Exception while parsing token: " + ex.getMessage());
 * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 * response.setContentType(MediaType.TEXT_PLAIN_VALUE);
 * response.getWriter().write("Invalid or expired token"); return; } }
 * 
 * filterChain.doFilter(request, response); }
 * 
 * }
 */