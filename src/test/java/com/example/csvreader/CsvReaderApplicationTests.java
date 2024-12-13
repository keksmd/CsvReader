package com.example.csvreader;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = CsvLoaderApplication.class)
class CsvReaderApplicationTests {




    @Test
    void contextLoads() {

    }


}
