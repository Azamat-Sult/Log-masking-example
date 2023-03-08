package com.example.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.example.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogGeneratorTest {

    private static final String LOGGER_NAME = "LogMasking";

    @Test
    void logMaskingTest() throws JsonProcessingException {
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        MemoryAppender memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.TRACE);
        logger.addAppender(memoryAppender);
        memoryAppender.start();

        ObjectMapper objectMapper = new ObjectMapper();
        String person = objectMapper.writeValueAsString(new Person("Name", "Moscow", "123456789"));
        System.out.println(person);
        logger.info(person);

        assertEquals(memoryAppender.countEventsForLogger(LOGGER_NAME), 1);
        assertTrue(memoryAppender.contains("{\"name\":\"Name\",\"address\":\"Moscow\",\"phone\":\"123456789\"}", Level.INFO));

        memoryAppender.reset();
        memoryAppender.stop();
    }

}