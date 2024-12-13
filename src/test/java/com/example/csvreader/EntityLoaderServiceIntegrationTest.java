// src/test/java/com/example/csvreader/service/EntityLoaderServiceIntegrationTest.java
package com.example.csvreader;

import com.example.csvreader.c.ExecutorTestConfig;
import com.example.csvreader.data.dto.EntityDto;
import com.example.csvreader.data.entity.Entity;
import com.example.csvreader.repository.EntityRepository;
import com.example.csvreader.service.EntityLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ActiveProfiles("test")
@SpringBootTest(classes = {EntityLoaderService.class, ExecutorTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EntityLoaderServiceIntegrationTest {

    @Autowired
    private EntityLoaderService entityLoaderService;

    @MockBean
    private EntityRepository repository;

    @MockBean
    private EntityMapper mapper;

    @Test
    void testConcurrentUpserts() throws InterruptedException {
        // Подготовка данных
        EntityDto dto1 = new EntityDto();
        dto1.setCode("CODE001");
        dto1.setName("Product A");
        dto1.setDescription("Description A");
        dto1.setStartDate(LocalDateTime.now());
        dto1.setModifiedOn(LocalDateTime.now());

        EntityDto dto2 = new EntityDto();
        dto2.setCode("CODE001"); // Тот же код для проверки обновления
        dto2.setName("Product A Updated");
        dto2.setDescription("Description A Updated");
        dto2.setStartDate(LocalDateTime.now());
        dto2.setModifiedOn(LocalDateTime.now());

        List<EntityDto> list1 = Arrays.asList(dto1);
        List<EntityDto> list2 = Arrays.asList(dto2);

        Entity entity1 = new Entity();
        entity1.setCode("CODE001");
        entity1.setName("Product A");
        entity1.setDescription("Description A");
        entity1.setStartDate(Timestamp.valueOf(dto1.getStartDate()));
        entity1.setModifiedOn(Timestamp.valueOf(dto1.getModifiedOn()));

        Entity entity2 = new Entity();
        entity2.setCode("CODE001");
        entity2.setName("Product A Updated");
        entity2.setDescription("Description A Updated");
        entity2.setStartDate(Timestamp.valueOf(dto2.getStartDate()));
        entity2.setModifiedOn(Timestamp.valueOf(dto2.getModifiedOn()));

        when(mapper.toEntity(dto1)).thenReturn(entity1);
        when(mapper.toEntity(dto2)).thenReturn(entity2);

        Thread thread1 = new Thread(() -> entityLoaderService.loadEntityList(list1));
        Thread thread2 = new Thread(() -> entityLoaderService.loadEntityList(list2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


        verify(repository).upsertEntity(entity1);
        verify(repository).upsertEntity(entity2);
    }
}
