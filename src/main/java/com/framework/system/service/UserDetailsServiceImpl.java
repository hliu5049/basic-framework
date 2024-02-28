package com.framework.system.service;

import com.framework.security.CustomUserDetails;
import com.framework.system.model.User;
import com.framework.system.model.UserRoleBind;
import com.framework.system.repository.UserRepository;
import com.framework.system.repository.UserRoleBindRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleBindRepository roleBindRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }
        val userId = user.getId();
        val roleBind = roleBindRepository.findByUserId(userId);
        user.setUserRoleBind(roleBind);
        return new CustomUserDetails(user);
    }
}
