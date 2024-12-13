package com.example.csvreader;

import com.example.csvreader.data.dto.EntityDto;
import com.example.csvreader.data.entity.Entity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface EntityMapper {
    Entity toEntity(EntityDto entityDto);
    EntityDto toEntityDto(Entity entity);
    default Timestamp map(LocalDateTime value){
        return Timestamp.valueOf(value);
    }
}