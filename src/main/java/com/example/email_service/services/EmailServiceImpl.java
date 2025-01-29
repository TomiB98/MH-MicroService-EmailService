package com.example.email_service.services;

import com.example.email_service.dtos.OrderEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${EMAIL_1}") // Inyecta la variable de entorno
    private String email;

    public void sendOrderEmail(OrderEmailDTO orderEmailDTO) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Orden creada para el usuario ID: ").append(orderEmailDTO.getUserId()).append("\n");
        emailContent.append("Total: ").append(orderEmailDTO.getTotalAmount()).append("\n\n");
        emailContent.append("Detalles de los productos:\n");

        for (OrderEmailDTO.OrderItemDTO item : orderEmailDTO.getItems()) {
            emailContent.append("- Producto: ").append(item.getProductName())
                    .append(" (ID: ").append(item.getProductId()).append(")\n")
                    .append("  Precio: ").append(item.getProductPrice())
                    .append(", Cantidad: ").append(item.getQuantity()).append("\n\n");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email); // Cambia esto
        message.setSubject("Orden creada");
        message.setText(emailContent.toString());

        emailSender.send(message);
    }
}