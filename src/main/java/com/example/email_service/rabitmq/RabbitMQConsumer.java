package com.example.email_service.rabitmq;

import com.example.email_service.services.EmailService;
import com.example.email_service.dtos.OrderEmailDTO;
import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "orderEmailQueue")
    public void receiveOrderEmail(OrderEmailDTO orderEmailDTO) throws MessagingException, IOException {
        System.out.println("Orden recibida: " + orderEmailDTO);
        emailService.sendOrderEmail(orderEmailDTO);
    }
}
