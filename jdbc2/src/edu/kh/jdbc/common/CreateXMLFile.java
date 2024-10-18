package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {

		// XML (eXtensible Markup Language) : 단순화된 데이터 기술 형식
		
		// XML에 저장되는 데이터 형식 Key : Value(Map)형식이다.
		// -> Key, Value 모두 String(문자열) 형식
		
		// XML 파일을 읽고, 쓰기 위한 IO 관련 클랜스 필요
		
		// * Properites 컬렉션 객체 *
		// - Map의 후손 클래스
		// - Key, Value 모두 String(문자열 형식)
		// - XMl 파일을 읽고, 쓰는데 특화된 메서드 제공
		
		/*
		 * <welcome-file-list>
		 * 	<welcome-file>index.html</welcome-file>
		 * 	<welcome-file>index.jsp</welcome-file> 
		 * </welcome-file-list>
		 */
		
		try {
			
			Scanner sc = new Scanner(System.in);
			
			// Properties 객체 생성
			// * Properties는 K:V가 모두 String으로 제한된 Map *
			Properties prop = new Properties();
			
			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();
			
			// FileOutputStream 생성
			FileOutputStream fos = new FileOutputStream(fileName + ".xml");
			
			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName + ".xml file!!!");
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
