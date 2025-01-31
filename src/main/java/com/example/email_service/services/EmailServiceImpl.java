package com.example.email_service.services;

import com.example.email_service.dtos.OrderEmailDTO;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${EMAIL_1}") // Inyecta la variable de entorno
    private String email;

    @Override
    public void sendWelcomeEmail(String registerEmail) {
        String emailContent = "Bienvenido a nuestro microservicio!" + "\n\n" +
                "Se ha registrado correctamente con el email: " + registerEmail + "\n\n" +
                "Ya puede loggearse y comenzar a realizar ordenes \n";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(registerEmail); // Cambia esto
        message.setSubject("Su registro ah sido exitoso!");
        message.setText(emailContent);

        emailSender.send(message);
    }





    public void sendOrderEmail(OrderEmailDTO orderEmailDTO) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // Soporta adjuntos

        helper.setTo(email);
        helper.setSubject("Pedido creado exitosamente");

        // Construcción del cuerpo del email
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Felicidades su orden ah sido creada se le informara cuando sea enviada.").append("\n\n");
        emailContent.append("Orden creada para el usuario: ").append(orderEmailDTO.getUserEmail()).append("\n");
        emailContent.append("Total de la orden: ").append(orderEmailDTO.getTotalAmount()).append("$ \n\n");
        emailContent.append("# Detalles de los productos:\n");

        for (OrderEmailDTO.OrderItemDTO item : orderEmailDTO.getItems()) {
            emailContent.append("- Producto: ").append(item.getProductName()).append("\n")
                    .append("- Precio: ").append(item.getProductPrice()).append("$ \n")
                    .append("- Cantidad: x").append(item.getQuantity()).append("\n\n");
        }

        helper.setText(emailContent.toString());

        // Generar el PDF y adjuntarlo
        byte[] pdfBytes = generateOrderPDF(orderEmailDTO);
        DataSource dataSource = new ByteArrayDataSource(pdfBytes, "application/pdf");
        helper.addAttachment("Orden.pdf", dataSource);

        emailSender.send(message);
    }

    private byte[] generateOrderPDF(OrderEmailDTO orderEmailDTO) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Orden de Compra");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Usuario: " + orderEmailDTO.getUserEmail());
                contentStream.newLine();
                contentStream.showText("Total: $" + orderEmailDTO.getTotalAmount());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Detalles de los productos:");
                contentStream.newLine();
                contentStream.newLine();

                for (OrderEmailDTO.OrderItemDTO item : orderEmailDTO.getItems()) {
                    contentStream.showText("- Producto: " + item.getProductName() +
                            " (ID: " + item.getProductId() + ")");
                    contentStream.newLine();
                    contentStream.showText("  Precio: " + item.getProductPrice() +
                            "$ x" + item.getQuantity());
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}