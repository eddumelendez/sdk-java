package io.cloudevents.springboot.autoconfigure;

import io.cloudevents.CloudEvent;
import io.cloudevents.spring.codec.CloudEventDecoder;
import io.cloudevents.spring.codec.CloudEventEncoder;
import io.rsocket.RSocket;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eddú Meléndez
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({CloudEvent.class, CloudEventEncoder.class, RSocket.class, RSocketStrategiesCustomizer.class})
@AutoConfigureBefore(RSocketStrategiesAutoConfiguration.class)
@ConditionalOnProperty(name = "cloudevents.spring.rsocket.enabled", havingValue = "true", matchIfMissing = true)
public class RSocketAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RSocketStrategiesCustomizer cloudEventsCustomizer() {
        return strategies -> {
            strategies.encoder(new CloudEventEncoder());
            strategies.decoder(new CloudEventDecoder());
        };
    }

}
