package com.example.email_service.services;

import com.example.email_service.dtos.OrderEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendOrderEmail(String to, OrderEmailDTO orderEmailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("testgrupo001@gmail.com");
            message.setTo(to);
            message.setSubject("Detalles de tu orden");

            // Construir el mensaje del correo con los detalles de la orden
            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Gracias por tu compra!\n\n");
            emailContent.append("Detalles de la orden:\n");
            for (OrderEmailDTO.OrderItemEmailDTO item : orderEmailDTO.getOrderItems()) {
                emailContent.append("- Producto: ").append(item.getProductName())
                        .append("\n  Precio: $").append(item.getProductPrice())
                        .append("\n  Cantidad: ").append(item.getQuantity())
                        .append("\n\n");
            }
            emailContent.append("Total: $").append(orderEmailDTO.getTotal());

            message.setText(emailContent.toString());

            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error al enviar el email: " + e.getMessage());
        }
    }
}