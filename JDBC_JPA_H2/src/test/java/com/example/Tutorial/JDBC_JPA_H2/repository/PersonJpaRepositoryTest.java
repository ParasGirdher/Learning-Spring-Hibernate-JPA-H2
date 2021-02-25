package com.example.Tutorial.JDBC_JPA_H2.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.Tutorial.JDBC_JPA_H2.entity.Person;


//The testcase when gets run, automatically launch spring context (JdbcJpaAH2Application.class)
@SpringBootTest
class PersonJpaRepositoryTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PersonJpaRepository personJpaRepository;

	@Test
	public void findByIdTest() {
		Person person = personJpaRepository.findById(2L);
		assertEquals("Paras", person.getName());
		logger.info(" First test launched successfully");
	}

	@Test
	public void findByIdTest_2() {
		Person person = personJpaRepository.findById(2L);
		assertNotEquals("Prince", person.getName());
		logger.info(" Second test launched successfully");
	}

	@Test
	@DirtiesContext // It would reset the data after the test, as it was before the test
	public void deleteById() {
		personJpaRepository.deleteById(1L);
		assertNull(personJpaRepository.findById(1L));
	}

	@Test
	@DirtiesContext
	public void save() {
		Person person = personJpaRepository.findById(2L);
		assertEquals("Kalanaur", person.getLocation());
		person.setLocation("Brighton");
		personJpaRepository.save(person);
		Person person1 = personJpaRepository.findById(2L);
		assertEquals("Brighton", person1.getLocation());
	}

}
