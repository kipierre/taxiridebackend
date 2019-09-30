package biz.advance_it_group.taxiride_backend.authentification.services.impl;

import biz.advance_it_group.taxiride_backend.authentification.entities.Roles;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.repositories.UserRepository;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service(value="userDetailsService")

public  class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Users not found with email nor phone : " + username)
                );

        return  CustomUserDetails.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Users user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Users :"+id)
        );

        return   CustomUserDetails.create(user);
    }
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Roles> roles) {
        return getGrantedAuthorities(getRoles(roles));
    }

    private List<String> getRoles(Collection<Roles> roles) {
        List<String> role = new ArrayList<String>();
        List<Roles> collection = new ArrayList<Roles>();

        return role;
    }

    private List<GrantedAuthority> getGrantedAuthorities( List<String> Roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for ( String Role : Roles) {
            authorities.add(new SimpleGrantedAuthority(Role));
        }
        return authorities;
    }
}


