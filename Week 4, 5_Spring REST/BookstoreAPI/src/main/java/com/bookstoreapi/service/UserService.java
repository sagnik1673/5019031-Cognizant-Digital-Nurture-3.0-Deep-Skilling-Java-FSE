package com.bookstoreapi.service;

import com.bookstoreapi.entity.User;
import com.bookstoreapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }

    @Transactional
    public User registerNewUser(User user) {
        // Check if the user already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User with username already exists.");
        }

        // Save the new user
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        // Find existing user
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getId()));

        // Update user details
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());  // You should hash the password before saving
        existingUser.setRoles(user.getRoles());

        // Save updated user
        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        userRepository.deleteById(userId);
    }
}