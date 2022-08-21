package com.html_parser.controller;

import com.html_parser.service.model.PrintService;
import com.html_parser.service.vo.PrintInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
		
		@PostMapping( "/print" )
		public Map backendTask( @RequestBody PrintInfo printInfo ) {
				
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put( "result","FAIL" );
				
				if( printInfo == null ) {
					return resultMap;
				}
				
				try{
						PrintService printService = new PrintService();
						resultMap = printService.backendTask( printInfo );
				}
				catch( Exception e ){
						resultMap.put( "msg", e.getMessage() );
				}
				
				return resultMap;
		}
		

}
