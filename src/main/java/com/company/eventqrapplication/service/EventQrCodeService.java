package com.company.eventqrapplication.service;

import com.company.eventqrapplication.entity.EventRequest;
import com.company.eventqrapplication.entity.User;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class EventQrCodeService {
    public byte[] generateQrCode(String qrText) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());

            BitMatrix matrix = new MultiFormatWriter()
                    .encode(qrText, BarcodeFormat.QR_CODE, 300, 300, hints);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка генерации QR-кода", e);
        }
    }

    public byte[] generateForParticipant(EventRequest req, User user) {
        String text = String.join("\n",
                "Код мероприятия: " + req.getEventCode(),
                "Мероприятие: " + req.getEventName(),
                "Зал: " + req.getEventHall(),
                "Дата: " + req.getEventDate().toString(),
                "Начало: " + req.getTimeStart().toString(),
                "Окончание: " + req.getTimeEnd().toString(),
                "Фамилия: " + user.getLastName(),
                "Имя: " + user.getFirstName(),
                "Организация: " + user.getDepartment()
        );

        return generateQrCode(text);
    }
}
