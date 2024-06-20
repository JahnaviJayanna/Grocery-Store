package io.swagger.util;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class ResponseUtil {

    public String generateRequestId(){
        return UUID.randomUUID().toString();
    }

    public String formatDateInGivenFormat(String format){
        // Format LocalDateTime using a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().format(formatter);
    }
}
