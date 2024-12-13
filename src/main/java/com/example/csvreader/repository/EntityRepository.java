package com.example.csvreader.repository;


import com.example.csvreader.data.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository extends JpaRepository<Entity, UUID> {

    @Modifying
    @Transactional
    @Query(value =
            "INSERT INTO entity (id, code, name, description, start_date,modified_on) " +
            "VALUES (?, ?, ?, ?, ?,?) " +
            "ON CONFLICT (code) DO UPDATE SET " +
            "name = EXCLUDED.name, " +
            "description = EXCLUDED.description, " +
            "start_date = EXCLUDED.start_date, "+
            "modified_on = EXCLUDED.modified_on",
            nativeQuery = true)
    void upsertValues(UUID id, String code, String name, String description, Timestamp start_date, Timestamp modify);
    @Transactional
    @Modifying
    default void upsertEntity(Entity entity){
        this.upsertValues(entity.getId(), entity.getCode(), entity.getName(), entity.getDescription(), entity.getStartDate(),Timestamp.valueOf(LocalDateTime.now()));
    }

    Optional<Entity> findByCode(String code);

}
