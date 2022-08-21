package com.html_parser.service.model;

import com.html_parser.service.vo.PrintInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class PrintServiceTest {

		@Autowired
		PrintService printService;
		
		@Test
		public void backendTask_성공테스트1() throws Exception {
				
				PrintInfo printInfo = new PrintInfo();
				printInfo.setUrl( "http://naver.com" );
				printInfo.setType( "01" );		// 01 : html 태그 제거, 02 : text 전체
				printInfo.setGroupCnt( 10 );
				
				Map<String, Object> resultMap = printService.backendTask( printInfo );
				System.out.println( "resultMap = " + resultMap.toString() );
		}
		
		@Test
		public void backendTask_성공테스트2() throws Exception {
				
				PrintInfo printInfo = new PrintInfo();
				printInfo.setUrl( "naver.com" );
				printInfo.setType( "02" );
				printInfo.setGroupCnt( 5 );
				
				Map<String, Object> resultMap = printService.backendTask( printInfo );
				System.out.println( "resultMap = " + resultMap.toString() );
		}
		
		@Test
		public void backendTask_URL_연결_실패테스트() throws Exception {
				
				PrintInfo printInfo = new PrintInfo();
				printInfo.setUrl( "naver" );
				printInfo.setType( "01" );
				printInfo.setGroupCnt( 10 );
				
				Map<String, Object> resultMap = printService.backendTask( printInfo );
				System.out.println( "resultMap = " + resultMap.toString() );
		}
		
		@Test
		public void backendTask_타입_예외_테스트() throws Exception {
				
				PrintInfo printInfo = new PrintInfo();
				printInfo.setUrl( "naver.com" );
				printInfo.setType( "99" );
				printInfo.setGroupCnt( 10 );
				
				Map<String, Object> resultMap = printService.backendTask( printInfo );
				System.out.println( "resultMap = " + resultMap.toString() );
		}
}
