//package com.example.notes.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.notes.models.Role;
//import com.example.notes.service.AuthService;
//
//@RestController
//@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000")  // Adjust frontend URL
//public class AuthController
//{
//	@Autowired
//	private AuthService authService;
//	
//    
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody Map<String, String> payload) 
//    {
//    	try {
//            String name = payload.get("name");
//            String email = payload.get("email");
//            String password = payload.get("password");
//            Role role = Role.valueOf(payload.get("role").toUpperCase());
//            String facultyCode = payload.get("facultyCode");
//
//            String response = authService.registerUser(name, email, password, role, facultyCode);
//            return ResponseEntity.ok(response);
//        } 
//    	catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed due to an error.");
//        }
//    }   
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        String password = payload.get("password");
//
//        // Validate input
//        if (email == null || password == null) {
//            return ResponseEntity.badRequest().body("Email and password are required.");
//        }
//
//        try {
//            String response = authService.loginUser(email, password);
//            String role = response.contains("Role:") ? response.split("Role:")[1].trim() : "";
//
//            Map<String, String> responseMap = new HashMap<>();
//            responseMap.put("role", role);
//            responseMap.put("message", "Login successful!");
//
//            return ResponseEntity.ok(responseMap);
//        } catch (Exception e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid email or password.");
//            return ResponseEntity.status(401).body(errorResponse);
//        }
//    }
//}


package com.example.notes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.models.Role;
import com.example.notes.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")  // Adjust frontend URL
public class AuthController
{
	@Autowired
	private AuthService authService;
	
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> payload) 
    {
    	try {
            String name = payload.get("name");
            String email = payload.get("email");
            String password = payload.get("password");
            Role role = Role.valueOf(payload.get("role").toUpperCase());
            String facultyCode = payload.get("facultyCode");

            String response = authService.registerUser(name, email, password, role, facultyCode);
            return ResponseEntity.ok(response);
        } 
    	catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Signup failed due to an error.");
        }
    }   
    
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        String password = payload.get("password");
//
//        // Validate input
//        if (email == null || password == null) {
//            return ResponseEntity.badRequest().body("Email and password are required.");
//        }
//
//        try {
//            // Call the authService to authenticate the user and get the role
//            String role = authService.loginUser(email, password); // Ensure this returns the role directly
//
//            // Create a response map with the correct role and message
//            Map<String, String> responseMap = new HashMap<>();
//            responseMap.put("role", role); // The role should be "STUDENT" or "FACULTY"
//            responseMap.put("message", "Login successful!"); // Success message
//
//            return ResponseEntity.ok(responseMap);
//        } catch (Exception e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid email or password.");
//            return ResponseEntity.status(401).body(errorResponse);
//        }
//    }
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
//        String email = payload.get("email");
//        String password = payload.get("password");
//
//        if (email == null || password == null) {
//            return ResponseEntity.badRequest().body("Email and password are required.");
//        }
//
//        try {
//            Map<String, Object> authResponse = authService.loginUser(email, password);
//
//            Map<String, Object> responseMap = new HashMap<>();
//            responseMap.put("role", authResponse.get("role"));
//            responseMap.put("teacherId", authResponse.get("id")); // Send teacher ID to frontend
//            responseMap.put("message", "Login successful!");
//
//            return ResponseEntity.ok(responseMap);
//        } catch (Exception e) {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("error", "Invalid email or password.");
//            return ResponseEntity.status(401).body(errorResponse);
//        }
//    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        try {
            Map<String, Object> authResponse = authService.loginUser(email, password);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("name", authResponse.get("name")); // ✅ Include user's name
            responseMap.put("role", authResponse.get("role"));
            responseMap.put("teacherId", authResponse.get("id")); // ✅ Generalized to "id" for all users
            responseMap.put("message", "Login successful!");

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid email or password.");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }



}
