package ru.sodajavadev.eat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "loot", schema = "eat")
public class Loot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "loot_name", nullable = false, length = 1000)
    private String lootName;

    @Column(name = "loot_cost", nullable = false)
    private Integer lootCost;

    @ManyToMany(mappedBy = "loot")
    private List<Event> events = new ArrayList<>();
}