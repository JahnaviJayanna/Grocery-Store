package io.swagger.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.swagger.model.InventoryItemDetailsPayload;
import io.swagger.model.InventoryItemDetailsPayloadDeserializer;
import org.springframework.validation.SmartValidator;

public class InventoryModule extends SimpleModule {

    public InventoryModule(SmartValidator validator) {
        addDeserializer(InventoryItemDetailsPayload.class, new InventoryItemDetailsPayloadDeserializer(validator));
    }
}