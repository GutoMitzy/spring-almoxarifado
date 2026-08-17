package com.br.almoxarifado.almoxarifado.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.queue.name}")
    private String queueName;


    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter,
            StatelessRetryOperationsInterceptor retryOperationsInterceptor) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setAdviceChain(retryOperationsInterceptor);
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() throws Exception{
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);

        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() throws Exception {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    public Queue createQueue() throws Exception {
        Queue q = QueueBuilder.durable(queueName).build();
        amqpAdmin().declareQueue(q);
        return q;
    }

    @Bean
    public Queue createBoqQueue() throws Exception {
        Queue boq = QueueBuilder.durable("BOQ." + queueName).build();
        amqpAdmin().declareQueue(boq);
        return boq;
    }

    @Bean
    public RepublishMessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        RepublishMessageRecoverer recoverer = new RepublishMessageRecoverer(rabbitTemplate);
        recoverer.setErrorRoutingKeyPrefix("BOQ.");
        return recoverer;
    }

    @Bean
    public StatelessRetryOperationsInterceptor retryOperationsInterceptor(
            RepublishMessageRecoverer recoverer) {
        return RetryInterceptorBuilder
                .stateless()
                .maxRetries(2)
                .backOffOptions(2000, 1, 100000)
                .recoverer(recoverer)
                .build();

    }
}
