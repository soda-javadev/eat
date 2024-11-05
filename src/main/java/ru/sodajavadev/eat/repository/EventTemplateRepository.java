package ru.sodajavadev.eat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sodajavadev.eat.entity.EventTemplate;

import java.util.List;

@Repository
public interface EventTemplateRepository extends JpaRepository<EventTemplate, Long> {

    List<EventTemplate> findAllByActiveIsTrue();

    @Modifying
    @Query("""
            DELETE FROM EventTemplate et
            WHERE et.id = :id
            """)
    int deleteByEventTemplateId(Long id);

    @Query(value = """
            SELECT count(*) > 0
            FROM event_template et
            WHERE et.template_name = :eventTemplateName
            """,
            nativeQuery = true)
    boolean isEventTemplateNameExists(String eventTemplateName);

}
