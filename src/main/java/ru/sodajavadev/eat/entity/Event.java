package ru.sodajavadev.eat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "event", schema = "eat")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_name", nullable = false, length = 1000)
    private String eventName;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @ColumnDefault("false")
    @Column(name = "successfully", nullable = false)
    private Boolean successfully = false;

}