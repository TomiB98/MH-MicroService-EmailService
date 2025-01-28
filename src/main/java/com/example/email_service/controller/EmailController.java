package com.example.email_service.controller;

import com.example.email_service.dtos.OrderEmailDTO;
import com.example.email_service.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Value("${EMAIL_1}") // Inyecta la variable de entorno
    private String email;

    @PostMapping("/order")
    public ResponseEntity<String> sendOrderEmail(@RequestBody OrderEmailDTO orderEmailDTO) {
        emailService.sendOrderEmail(email, orderEmailDTO);
        return ResponseEntity.ok("Order email sent successfully.");
    }

}
