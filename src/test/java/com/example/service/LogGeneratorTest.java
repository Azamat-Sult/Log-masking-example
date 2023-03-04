package com.example.service;

import com.example.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class LogGeneratorTest {

    @Test
    void logMaskingTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String person = objectMapper.writeValueAsString(new Person("Name", "Moscow", "123456789"));
        System.out.println(person);
        log.info(person);
    }

}