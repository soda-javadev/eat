package ru.sodajavadev.eat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sodajavadev.eat.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
