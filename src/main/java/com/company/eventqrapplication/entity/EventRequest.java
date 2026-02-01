package com.company.eventqrapplication.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "EQA_EVENT_REQUEST")
@Entity(name = "eqa_EventRequest")
public class EventRequest {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "EVENT_CODE")
    private String eventCode;

    @Column(name = "EVENT_NAME")
    private String eventName;

    @JoinColumn(name = "EVENT_HALL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall eventHall;

    @Column(name = "EVENT_DATE")
    private LocalDate eventDate;

    @Column(name = "TIME_START")
    private LocalTime timeStart;

    @Column(name = "TIME_END")
    private LocalTime timeEnd;

    @InstanceName
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "ORGANIZATION")
    private String organization;

    @Column(name = "QR_CODE")
    private byte[] qrCode;

    @OneToMany(mappedBy = "eventRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<EventParticipant> participants;

    @Column(name = "REQUEST_DATE")
    private LocalDate requestDate;

    @Column(name = "NUMBER_")
    private Integer number;

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setEventHall(Hall eventHall) {
        this.eventHall = eventHall;
    }

    public Hall getEventHall() {
        return eventHall;
    }

    public List<EventParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<EventParticipant> participants) {
        this.participants = participants;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}