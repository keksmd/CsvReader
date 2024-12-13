package com.example.csvreader.service;


import com.example.csvreader.EntityMapper;
import com.example.csvreader.data.dto.EntityDto;
import com.example.csvreader.data.entity.Entity;
import com.example.csvreader.repository.EntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EntityLoaderServiceTest {

    @Mock
    private EntityRepository repository;

    @Mock
    private ExecutorService executorService;

    @Mock
    private EntityMapper mapper;

    @InjectMocks
    private EntityLoaderService entityLoaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadEntityList() {
        // Подготовка данных
        EntityDto dto1 = new EntityDto();
        dto1.setCode("CODE001");
        dto1.setName("Product A");
        dto1.setDescription("Description A");
        dto1.setStartDate(LocalDateTime.now());
        dto1.setModifiedOn(LocalDateTime.now());


        List<EntityDto> dtoList = Arrays.asList(dto1);

        Entity entity1 = new Entity();
        entity1.setCode("CODE001");
        entity1.setName("Product A");
        entity1.setDescription("Description A");
        entity1.setStartDate(Timestamp.valueOf(dto1.getStartDate()));
        entity1.setModifiedOn(Timestamp.valueOf(dto1.getModifiedOn()));


        when(mapper.toEntity(any(EntityDto.class))).thenReturn(entity1);

         entityLoaderService.loadEntityList(dtoList);





    }
}