package org.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    /**
     * Exchange 생성
     * @return
     */
    @Bean
    public DirectExchange directExchange() {

        return new DirectExchange("delivery.exchange");
    }

    /**
     * Queue 생성
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("delivery.queue");
    }

    /**
     * Exchange 와 Queue를 Binding
     * @param queue
     * @param directExchange
     * @return
     */
    @Bean
    public Binding binding(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }

    /**
     * End Queue 설정
     * @param connectionFactory yaml file에 정의
     * @param messageConverter
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(
        ConnectionFactory connectionFactory, MessageConverter messageConverter
    ) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    /**
     * Object -> Json -> Object
     * @param objectMapper
     * @return
     */
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
