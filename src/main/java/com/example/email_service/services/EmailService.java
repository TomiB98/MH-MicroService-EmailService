package com.example.email_service.services;

import com.example.email_service.dtos.OrderEmailDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendOrderEmail(OrderEmailDTO orderEmailDTO);

}
