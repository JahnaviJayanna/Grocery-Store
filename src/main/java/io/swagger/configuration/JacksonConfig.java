package io.swagger.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.model.InventoryItemDetailsPayload;
import io.swagger.model.InventoryItemDetailsPayloadDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.SmartValidator;

@Configuration
public class JacksonConfig {

    @Autowired
    private SmartValidator validator;

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder(SmartValidator validator) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modulesToInstall(new InventoryModule(validator));
        return builder;
    }

    @Bean
    public SimpleModule customModule(SmartValidator validator) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(InventoryItemDetailsPayload.class, new InventoryItemDetailsPayloadDeserializer(validator));
        return module;
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build();
    }

//    static class InventoryModule extends SimpleModule {
//        public InventoryModule(SmartValidator validator) {
//            addDeserializer(InventoryItemDetailsPayload.class, new InventoryItemDetailsPayloadDeserializer(validator));
//        }
//    }
}
