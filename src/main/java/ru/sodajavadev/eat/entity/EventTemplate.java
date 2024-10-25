package ru.sodajavadev.eat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_template", schema = "eat")
public class EventTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "template_name", nullable = false, length = 1000)
    private String templateName;

    @Column(name = "event_name", nullable = false, length = 1000)
    private String eventName;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "minute")
    private Integer minute;

    @Column(name = "hour")
    private Integer hour;

    @Column(name = "day_of_week", length = 10)
    private String dayOfWeek;

    @Column(name = "day_of_month")
    private Integer dayOfMonth;

    @Column(name = "active")
    private Boolean active;

}