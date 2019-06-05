package com.cherentsov.AnimalFront.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AnimalFrontApplicationTests {

	@Test
	public void main(){
		//Проверка загрузки контекта
		System.out.println("Проверка загрузки контекста");
	}
}
