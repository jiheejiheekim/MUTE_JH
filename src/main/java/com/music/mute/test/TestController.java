package com.music.mute.test;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
    public TestController(TestService testService) {
        this.testService = testService;
    }
    @GetMapping("/test1")
    public String showTestPage1(HttpSession session) {
        return testService.showTestPage1(session);
    }
    @GetMapping("/test2")
    public String showTestPage2() {
        return testService.showTestPage2();
    }
    @GetMapping("/test3")
    public String showTestPage3() {
        return testService.showTestPage3();
    }
    @GetMapping("/test4")
    public String showTestPage4() {
        return testService.showTestPage4();
    }
    @GetMapping("/test5")
    public String showTestPage5() {
        return testService.showTestPage5();
    }
    @GetMapping("/test6")
    public String showTestPage6() {
        return testService.showTestPage6();
    }
    @GetMapping("/test7")
    public String showTestPage7() {
        return testService.showTestPage7();
    }
    @GetMapping("/test8")
    public String showTestPage8() {
        return testService.showTestPage8();
    }
    @PostMapping("/updategenres")
    public ResponseEntity<String> updateGenres(@RequestBody int[] genres, HttpSession session) {
        return testService.updateGenres(genres, session);
    }
	 // 테스트를 통해 나온 최종 배열 인덱스 중 최댓값에 해당하는 결과페이지로 GetMapping
    @GetMapping("/result_genres")
    public String showResultPage(HttpSession session) {
        return testService.showResultPage(session);
    }
}///////////////////////////////////
