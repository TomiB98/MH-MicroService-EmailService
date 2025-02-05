package com.example.email_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EMAIL_QUEUE = "orderEmailQueue";
    private static final String EMAIL_EXCHANGE = "orderEmailExchange";

    private static final String WELCOME_EMAIL_QUEUE = "welcomeEmailQueue";
    private static final String WELCOME_EMAIL_EXCHANGE = "welcomeEmailExchange";

    private static final String VERIFICATION_EMAIL_QUEUE = "verificationEmailQueue";
    private static final String VERIFICATION_EMAIL_EXCHANGE = "verificationEmailExchange";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Order Email
    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, false);
    }

    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with("order.email");
    }

    // Welcome Email
    @Bean
    public Queue welcomeEmailQueue() {
        return new Queue(WELCOME_EMAIL_QUEUE, false);
    }

    @Bean
    public TopicExchange welcomeEmailExchange() {
        return new TopicExchange(WELCOME_EMAIL_EXCHANGE);
    }

    @Bean
    public Binding welcomeEmailBinding(Queue welcomeEmailQueue, TopicExchange welcomeEmailExchange) {
        return BindingBuilder.bind(welcomeEmailQueue).to(welcomeEmailExchange).with("welcome.email");
    }

    // Verification Email
    @Bean
    public Queue verificationEmailQueue() {
        return new Queue(VERIFICATION_EMAIL_QUEUE, false);
    }

    @Bean
    public TopicExchange verificationEmailExchange() {
        return new TopicExchange(VERIFICATION_EMAIL_EXCHANGE);
    }

    @Bean
    public Binding verificationEmailBinding(Queue verificationEmailQueue, TopicExchange verificationEmailExchange) {
        return BindingBuilder.bind(verificationEmailQueue).to(verificationEmailExchange).with("verification.email");
    }
}
