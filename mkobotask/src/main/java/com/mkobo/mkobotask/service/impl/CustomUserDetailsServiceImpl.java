package com.mkobo.mkobotask.service.impl;

import com.mkobo.mkobotask.models.UserAccount;
import com.mkobo.mkobotask.repository.UserAccountRepository;
import com.mkobo.mkobotask.security.UserPrincipal;
import com.mkobo.mkobotask.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private UserAccountRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) {
		UserAccount user = userRepository.findByEmail(usernameOrEmail);
//				.orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
		return UserPrincipal.create(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserById(Long id) {
		UserAccount user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

		return UserPrincipal.create(user);
	}
}
