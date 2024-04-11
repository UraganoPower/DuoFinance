package com.wiley.DuoFinance;

import com.wiley.DuoFinance.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DuoFinanceApplicationTests {
	@Test
	public void testHome() {
		UserController controller = new UserController();
		Model model = new BindingAwareModelMap();
		assertEquals("Welcome to the Trivia Game!", model.getAttribute("message"));
	}

}
