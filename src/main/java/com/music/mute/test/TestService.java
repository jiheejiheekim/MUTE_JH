package com.music.mute.test;

import javax.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface TestService {

	 String showTestPage1(HttpSession session);
	 String showTestPage2();
	 String showTestPage3();
	 String showTestPage4();
	 String showTestPage5();
	 String showTestPage6();
	 String showTestPage7();
	 String showTestPage8();
	 
	 ResponseEntity<String> updateGenres(int[] genres, HttpSession session);
	 
	 String showResultPage(HttpSession session);
}
