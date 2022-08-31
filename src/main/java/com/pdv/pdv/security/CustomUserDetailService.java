package com.pdv.pdv.security;

import com.pdv.pdv.dto.LoginDTO;
import com.pdv.pdv.entity.User;
import com.pdv.pdv.exceptions.PasswordNotFoundException;
import com.pdv.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("Login inválido!");
        }


        return UserPrincipal.create(user);
    }

    public void verifyUserCredentials(LoginDTO login){
        UserDetails user = loadUserByUsername(login.getUsername());
        boolean passwordIsTheSame = SecurityConfig.passwordEncoder()
                .matches(login.getPassword(), user.getPassword());
                        //recebido               //password hashed
        if(!passwordIsTheSame){
            throw new PasswordNotFoundException("Senha invalida!");
        }
    }

}
