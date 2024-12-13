package com.example.csvreader.service;

import com.example.csvreader.EntityMapper;
import com.example.csvreader.data.dto.EntityDto;
import com.example.csvreader.repository.EntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EntityLoaderService {
    private final EntityRepository repository;

    private final EntityMapper mapper;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Modifying
    public void loadEntityList(List<EntityDto> values) {
        log.info("Загрузка {} значений ", values.size());
         values
                .stream()
                .map(mapper::toEntity)
                .forEach(repository::upsertEntity);
    }


}
