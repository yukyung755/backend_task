package com.html_parser.service.model;

import com.html_parser.service.vo.PrintInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PrintService {
		
		public Map<String, Object> backendTask ( PrintInfo printInfo ) throws Exception{
				
				Map<String, Object> result = new HashMap<>();
				
				// 1. 모든 문자 입력 가능 response 객체 획득
				String str = getResponse( printInfo.getUrl(), printInfo.getType() );
				
				if( !StringUtils.hasText( str ) ){
						return result;
				}
				
				// 2. 영어, 숫자만 출력
				String[] eng = getEngArr( str );
				String[] num = getNumArr( str );
				System.out.println();
				System.out.print( "추출된 영어 :: " );
				for ( String s : eng ) {
						System.out.print( s );
				}
				
				System.out.println();
				System.out.print("추출된 숫자 :: ");
				for ( String s : num ) {
						System.out.print( s );
				}
				
				// 3. 오름차순
				Arrays.sort( eng, String.CASE_INSENSITIVE_ORDER );
				Arrays.sort( num, String.CASE_INSENSITIVE_ORDER );
				System.out.println();
				System.out.println();
				System.out.print( "정렬된 영어 :: " );
				for ( String s : eng ) {
						System.out.print( s );
				}
				
				System.out.println();
				System.out.print( "정렬된 숫자 :: " );
				for ( String s : num ) {
						System.out.print( s );
				}
				
				// 4. 교차출력
				String[] mixedArr = getMixedArr( num, eng );
				System.out.println("");
				System.out.println("");
				System.out.print("교차된 배열 :: ");
				for ( String s : mixedArr ) {
						System.out.print( s );
				}
				
				// 5. 출력묶음단위
				int groupCnt = printInfo.getGroupCnt();
				System.out.println();
				System.out.println();
				System.out.println( "몫 :: " + mixedArr.length / groupCnt );
				System.out.println( "나머지 :: " + mixedArr.length % groupCnt );
		
				result.put( "quotient", mixedArr.length / groupCnt );
				result.put( "remainder", mixedArr.length % groupCnt );
				result.put( "result", "SUCCESS" );
				return result;
		}
		
		/**
		 * 문자열에서 영문자만 추출한다.
		 *
		 * @param str
		 * @return
		 */
		private String[] getEngArr( String str ) {
				
				Pattern nonValidPattern = Pattern.compile( "[a-zA-Z]" );
				Matcher matcher = nonValidPattern.matcher( str );
				String result = "";
				
				while ( matcher.find() ) {
						result += matcher.group();
				}
				
				return result.split("" );
		}
		
		/**
		 * 문자열에서 숫자만 추출한다
		 *
		 * @param str
		 * @return
		 */
		private String[] getNumArr( String str ) {
				
				String intStr = str.replaceAll("[^0-9]", "" );
				intStr.split("" );
				return intStr.split( "" );
		}
		
		/**
		 * 두 배열을 앞부터 믹스하여 리턴한다.
		 * 단, 앞자리가 영문자로 오게 믹스한다
		 *
		 * @param Arr1
		 * @param Arr2
		 * @return
		 */
		private String[] getMixedArr( String[] Arr1, String[] Arr2 ) {
				
				if ( Arr1.length == 0 || Arr2.length == 0 ) return new String[0];
				
				String[] bigArr = null;
				String[] smallArr = null;
				
				if ( Arr1.length >= Arr2.length ) {
						bigArr = Arr1.clone();
						smallArr = Arr2.clone();
				}
				else {
						bigArr = Arr2.clone();
						smallArr = Arr1.clone();
				}
				
				if( bigArr == null || smallArr == null ) return new String[0];
				
				int sort = 1;
				char c = bigArr[0].charAt(0);
				if ( !(c >= 'A' && c <= 'Z' ) && !( c >= 'a' && c <= 'z' ) ) {
						sort = 0;
				}
				
				for ( int i = 0; i < smallArr.length; i++ ) {
						System.arraycopy( smallArr, i, bigArr, i * 2 + sort, 1 );
				}
				
				return bigArr;
		}
		
		/**
		 * 클라이언트에서 입력받은 url로 request를 날려 response 획득.
		 * type에 따라 response를 치환한다.
		 * 01 : html 태그 제거
		 * 02 : text 전체
		 * 그외 : ""
		 *
		 * @param _url
		 * @param _type
		 * @return
		 * @throws Exception
		 */
		private String getResponse( String _url, String _type ) throws Exception{
				
				String result = "";
				
				if( !StringUtils.hasText( _url ) ) {
					return result;
				}
				
				if( !_url.startsWith( "http://" ) && !_url.startsWith( "https://" ) ){
					_url = "http://".concat( _url );
				}
				
				try {
						URL url = new URL( _url );
						HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
						connection.setRequestMethod( "GET" );
						connection.setConnectTimeout( 2000 );
						
						BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
						StringBuffer stringBuffer = new StringBuffer();
						String inputLine;
						
						while ( ( inputLine = bufferedReader.readLine() ) != null )  {
								stringBuffer.append( inputLine );
						}
						
						bufferedReader.close();
						
						result = stringBuffer.toString();
						
				}
				catch ( MalformedURLException e) {
						throw new Exception( "URL 형태가 올바르지 않습니다." );
				}
				catch ( IOException e) {
						throw new Exception( "URL 연결에 실패하였습니다." );
				}
				
				System.out.println( "응답원본 :: " + result );
				
				if ( "01".equals( _type ) ) {
						return result.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "" ).replaceAll("\\<.*?>", "" );
				}
				else if ( "02".equals( _type ) ) {
						return result;
				}
				else {
						return "";
				}
		}
}
