package ru.sodajavadev.eat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event", schema = "eat")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 1000)
    private String eventName;

    @Column(name = "time", nullable = false)
    private LocalDateTime eventTime;

    @ColumnDefault("false")
    @Column(name = "successfully", nullable = false)
    private Boolean successfully = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_template_id", nullable = false)
    private EventTemplate eventTemplate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mtm_event_loot",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "loot_id"))
    private List<Loot> loot = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mtm_event_guild_member",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "guild_member_id"))
    private List<GuildMember> guildMembers = new ArrayList<>();
}