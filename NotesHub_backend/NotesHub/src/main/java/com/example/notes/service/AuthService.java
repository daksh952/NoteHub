//package com.example.notes.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.example.notes.models.Role;
//import com.example.notes.models.User;
//import com.example.notes.repository.UserRepository;
//
//@Service
//public class AuthService
//{
//	 @Autowired
//	 private UserRepository userRepository;
//	 private final BCryptPasswordEncoder passwordEncoder;
//	 
//	 @Value("${app.faculty.special-code}")
//	    private String facultySpecialCode;
//	 
//	 public AuthService(UserRepository userRepository) {
//	        this.passwordEncoder = new BCryptPasswordEncoder();
//	    }
//	 
//	 public String registerUser(String name, String email, String password, Role role, String facultyCode) {
//		 if (userRepository.existsByEmail(email)) {
//	            throw new RuntimeException("Email already exists! Please use a different email.");
//	        }
//
//	        if (role == Role.FACULTY && (facultyCode == null || !facultyCode.equals(facultySpecialCode))) {
//	            return "Invalid Faculty Code!";
//	        }
//
//	        User user = new User();
//	        user.setName(name);
//	        user.setEmail(email);
//	        user.setPassword(passwordEncoder.encode(password));
//	        user.setRole(role);
//	        userRepository.save(user);
//
//	        return "User registered successfully!";
//	    }
//	 
//	 public String loginUser(String email, String password) {
//	        Optional<User> userOpt = userRepository.findByEmail(email);
//
//	        if (userOpt.isPresent()) {
//	            User user = userOpt.get();
//	            if (passwordEncoder.matches(password, user.getPassword())) {
//	                return "Login successful!";
//	            }
//	            return "Invalid credentials!";
//	        }
//	        return "User not found!";
//	    }
//}

package com.example.notes.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.notes.models.Role;
import com.example.notes.models.User;
import com.example.notes.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.faculty.special-code}")
    private String facultySpecialCode;

    public AuthService(UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(String name, String email, String password, Role role, String facultyCode) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists! Please use a different email.");
        }

        if (role == Role.FACULTY && (facultyCode == null || !facultyCode.equals(facultySpecialCode))) {
            return "Invalid Faculty Code!";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userRepository.save(user);

        return "User registered successfully!";
    }

//    public String loginUser(String email, String password) throws Exception {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return user.getRole().name(); // Return the role directly
//            }
//            throw new Exception("Invalid email or password.");
//        }
//        throw new Exception("Invalid email or password.");
//    }
    
    public Map<String, Object> loginUser(String email, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                response.put("role", user.getRole().name());  // Return role
                response.put("id", user.getId());  // Return teacherId (or userId)
                response.put("name", user.getName());
                return response;
            }
            throw new Exception("Invalid email or password.");
        }
        throw new Exception("Invalid email or password.");
    }

}
