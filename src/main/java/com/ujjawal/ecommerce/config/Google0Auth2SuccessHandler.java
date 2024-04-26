package com.ujjawal.ecommerce.config;

import com.ujjawal.ecommerce.model.Role;
import com.ujjawal.ecommerce.model.User;
import com.ujjawal.ecommerce.repository.RoleRepository;
import com.ujjawal.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*This annotation marks the class as a Spring component, allowing it to be automatically detected and registered by the Spring container during component scanning.
The class implements the AuthenticationSuccessHandler interface, which defines a method onAuthenticationSuccess to handle successful authentication.*/

@Component
public class Google0Auth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    /*This line initializes a RedirectStrategy instance, which is used to redirect users after successful authentication.
onAuthenticationSuccess method:*/
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String email = token.getPrincipal().getAttributes().get("email").toString();
            if(userRepository.findUserByEmail(email).isPresent()){
            }else{

                User user = new User();
                user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                List<Role> roles = new ArrayList<>();
                /*It checks if a user with the extracted email already exists in the database. If not, it creates a new user with the first name, last name, and role obtained from the OAuth2 token attributes.*/
                roles.add(roleRepository.findById(2).get());
                user.setRoles(roles);
                //for save users
                userRepository.save(user);
            }
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        }

    }

/*Overall, this class handles the logic for creating a new user in the system if they are logging in for the first time using Google OAuth2 and redirects them to the home page after authentication.
*/














