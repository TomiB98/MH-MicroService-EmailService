package com.example.email_service.services;

import com.example.email_service.dtos.OrderEmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface EmailService {

    void sendOrderEmail(OrderEmailDTO orderEmailDTO) throws MessagingException, IOException;
    void sendWelcomeEmail(String email);
}
