package auth_service.service.impl;

import auth_service.exception.UserNotFoundException;
import auth_service.model.entity.Role;
import auth_service.model.entity.UserRole;
import auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * 查詢 使用者的帳號，密碼，權限
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .map(user -> {
                    List<GrantedAuthority> authorities=user.getUserRoles().stream()
                            .map(UserRole::getUserRole)
                            .map(Role::getRole)
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    return new User(user.getUserName(),user.getUserPassword(),authorities);
                }).orElseThrow(()->new UserNotFoundException("找不到使用者"));
    }
}
