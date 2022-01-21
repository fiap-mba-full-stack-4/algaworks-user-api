package com.algaworks.userapi.core.usecase;

import com.algaworks.userapi.config.rabbitmq.RabbitMQMessageProducer;
import com.algaworks.userapi.entrypoint.request.email.WelcomeEmailRequest;
import com.algaworks.userapi.entrypoint.request.user.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailSender {

  private final RabbitMQMessageProducer producer;

  @Value("${rabbitmq.exchanges.internal}")
  private String internalExchange;

  @Value("${rabbitmq.routing-keys.internal-notification}")
  private String internalNotificationRoutingKey;

  public void sendWelcomeEmail(final String email, final String username) {
    log.info("## Publishing email notification to queue.");

    WelcomeEmailRequest emailRequest = WelcomeEmailRequest.builder()
        .userEmail(email)
        .userName(username).build();

    try {
      producer.publish(emailRequest, internalExchange, internalNotificationRoutingKey);
    } catch (Exception e) {
      log.error("Error publishing to queue. ", e.getMessage());
      return;
    }

    log.info("## Published with success.");
  }

}
