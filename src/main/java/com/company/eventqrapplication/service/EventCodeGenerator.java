package com.company.eventqrapplication.service;

import com.company.eventqrapplication.entity.EventRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EventCodeGenerator {

    public String generateEventCode(EventRequest req) {
        return String.format(
                "%d-%s/%02d[%s]",
                req.getNumber(),
                req.getRequestDate().getMonth().getValue(),
                req.getRequestDate().getYear() % 100,
                req.getRequestDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        );
    }
}