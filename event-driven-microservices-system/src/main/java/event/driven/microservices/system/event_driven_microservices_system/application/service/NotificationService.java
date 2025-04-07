package event.driven.microservices.system.event_driven_microservices_system.application;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = "order-placed", groupId = "notification-group")
    public void sendNotification(String message) {
        // Parse the message (assume it's JSON)
        String email = extractEmail(message);         // Extract customer email
        String orderDetails = extractOrderDetails(message); // Extract order details

        try {
            // Send an email
            sendEmail(email, orderDetails);
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    private void sendEmail(String to, String orderDetails) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Order Confirmation");
        helper.setText("Your order has been placed successfully:\n\n" + orderDetails);

        mailSender.send(message);
        System.out.println("Email sent to: " + to);
    }

    private String extractEmail(String message) {
        // Placeholder: parse email from JSON
        return "nguyennga200x@gmail.com";
    }

    private String extractOrderDetails(String message) {
        // Placeholder: parse order details from JSON
        return "Product: Laptop, Quantity: 1";
    }
}
