package com.company.eventqrapplication.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "EQA_EVENT_PARTICIPANT", indexes = {
        @Index(name = "IDX_EQA_EVENT_PARTICIPANT_USER", columnList = "USER_ID"),
        @Index(name = "IDX_EQA_EVENT_PARTICIPANT_EVENT_REQUEST", columnList = "EVENT_REQUEST_ID")
})
@Entity(name = "eqa_EventParticipant")
public class EventParticipant {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "EVENT_REQUEST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EventRequest eventRequest;

    @JoinColumn(name = "USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "QR_CODE")
    private byte[] qrCode;

    public EventRequest getEventRequest() {
        return eventRequest;
    }

    public void setEventRequest(EventRequest eventRequest) {
        this.eventRequest = eventRequest;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}