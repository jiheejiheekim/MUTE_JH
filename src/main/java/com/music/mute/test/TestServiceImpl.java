package com.music.mute.test;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class TestServiceImpl implements TestService {
	
	@Override
    public String showTestPage1(HttpSession session) {
        int[] genres = new int[10];
        session.setAttribute("musicGenres", genres);
        System.out.println("--------------------------");
        System.out.println("test1에서 생성되는 장르 배열");
        return "test/test1"; }
	@Override
    public String showTestPage2() {
        System.out.println("--------------------------");
        System.out.println("test2에서 생성되는 장르 배열");
        return "test/test2"; }
	@Override
    public String showTestPage3() {
        System.out.println("--------------------------");
        System.out.println("test3에서 생성되는 장르 배열");
        return "test/test3"; }
	@Override
    public String showTestPage4() {
        System.out.println("--------------------------");
        System.out.println("test4에서 생성되는 장르 배열");
        return "test/test4"; }
	@Override
    public String showTestPage5() {
        System.out.println("--------------------------");
        System.out.println("test5에서 생성되는 장르 배열");
        return "test/test5"; }
	@Override
    public String showTestPage6() {
        System.out.println("--------------------------");
        System.out.println("test6에서 생성되는 장르 배열");
        return "test/test6"; }
	@Override
    public String showTestPage7() {
        System.out.println("--------------------------");
        System.out.println("test7에서 생성되는 장르 배열");
        return "test/test7"; }
	@Override
    public String showTestPage8() {
        System.out.println("--------------------------");
        System.out.println("test8에서 생성되는 장르 배열");
        return "test/test8"; }
	
	@Override
    public ResponseEntity<String> updateGenres(int[] genres, HttpSession session) {
        int[] existingGenres = (int[]) session.getAttribute("musicGenres");// 세션에서 기존 장르 배열 가져오기
        // 1. 각 테스트 페이지 별 배열에 대한 로깅
        System.out.println("1. 각 테스트 페이지 별 배열: ");
        for (int i = 0; i < genres.length; i++) {
            System.out.print(genres[i] + " ");
        }
        System.out.println();
        // 2. 기존 장르 배열에 대한 로깅
        System.out.println("2. 기존 장르 배열: ");
        if (existingGenres != null) {
            for(int existingGenre : existingGenres) {
                System.out.print(existingGenre + " ");
            }
            System.out.println();
        }
        // 장르를 기존 배열에 더하는 로직
        if (existingGenres != null) {
            for(int i = 0; i < genres.length && i < existingGenres.length; i++) {
                existingGenres[i] += genres[i];
            }
        }else{// 기존 장르 배열이 비어있을 경우, 새로운 장르 배열로 초기화
            existingGenres = genres.clone();
        }
        // 3. 배열 추가 후 로깅
        System.out.println("3. 배열 추가 후: ");
        for(int existingGenre : existingGenres) {
            System.out.print(existingGenre + " ");
        }
        System.out.println();

        // 세션에 업데이트된 장르 배열 저장
        session.setAttribute("musicGenres", existingGenres);
        // ResponseEntity로 응답 반환
        return ResponseEntity.ok("good");
    }
	
	@Override
    public String showResultPage(HttpSession session) {
        int maxIndex = 0;
        int maxValue = 0;
        int[] resultGenres = (int[]) session.getAttribute("musicGenres");
        String defaultPage = "/main";

        if(resultGenres != null) {
            for(int i = 0; i < resultGenres.length; i++) {
                if(resultGenres[i] > maxValue) {
                    maxValue = resultGenres[i];
                    maxIndex = i;
                }
            }
        switch (maxIndex) {
            case 0:
                return "redirect:result_rock";
            case 1:
                return "redirect:result_folk";
            case 2:
                return "redirect:result_techno";
            case 3:
 			   return "redirect:result_r-n-b";
 		   case 4:
 			   return "redirect:result_hip-hop";
 		   case 5:
 			   return "redirect:result_classical";
 		   case 6:
 			   return "redirect:result_indie";
 		   case 7:
 			   return "redirect:result_disco";
 		   case 8:
 			   return "redirect:result_jazz";
 		   case 9:
 			   return "redirect:result_dance";
 		   default:
 		   	   return "/main";
        	}
        }
    return defaultPage; // 장르 배열이 비어있을 경우 기본 페이지 리턴
    }
}///////////////////////////////////////
