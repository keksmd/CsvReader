package com.example.csvreader.data.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@jakarta.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    private String code;
    private String name;
    private String description;
    private Timestamp startDate;
    private Timestamp modifiedOn;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Entity entity = (Entity) o;
        return getId() != null && Objects.equals(getId(), entity.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @PrePersist
    @PreUpdate
    public void modifieModifiedOn() {
        this.setModifiedOn(Timestamp.valueOf(java.time.LocalDateTime.now()));
    }

    @PostPersist
    @PostLoad
    public void postLoad() {
        log.info("значение {} загружено в {}",this.name,LocalDateTime.now().toString());
    }

}
