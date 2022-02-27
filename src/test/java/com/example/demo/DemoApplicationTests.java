package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DemoApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddNumber() {
		// given
		int numberOne = 20;
		int numberTwo = 10;

		// when
		int result = underTest.add(numberOne, numberTwo);

		// then
		int exceptedResult = 30;
		assertThat(result).isEqualTo(exceptedResult);
	}

	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}

}
