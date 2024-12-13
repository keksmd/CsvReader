package com.example.csvreader.data.dto;

import com.example.csvreader.data.entity.Entity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for {@link Entity}
 */
@Data
public class EntityDto {
    private String code;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime modifiedOn;
}