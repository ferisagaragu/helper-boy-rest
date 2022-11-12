package org.pechblenda.helperboyrest.service

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql

import javax.transaction.Transactional

import org.junit.jupiter.api.TestInstance

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = ["classpath:application-test.properties"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql(scripts = [], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FoodServiceTest { }