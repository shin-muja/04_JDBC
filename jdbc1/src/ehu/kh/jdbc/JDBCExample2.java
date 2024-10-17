package ehu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		// 입력 받은 급여보다 초과해서 받는 사원의
		// 사번, 이름, 급여 조회
		
		// 1. JDBC 객체 참조용 변수 선언
		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		// 2. DriverManager 객체를 이용해서 Connection 객체 생성
		// 2-1) Orale JDBC Driver 객체 메모리 로드
		// 2-2) DB 연결 정보 작성
		// 2-3) DB 연결 정보와 DriverManager를 이용해서 Connection 객체 생성
		
		try {
			String type = "jdbc:oracle:thin:@";
			
			String host = "localhost";
			
			String port = ":1521";
			
			String dbName= ":XE";
			
			String userName = "kh_sdk";
			
			String password = "kh1234";
			
			conn = DriverManager.getConnection(type + host + port + dbName ,userName, password);
			
			// 3. SQL 작성
			// 입력 받은 급여 -> Scanner 필요
			// int input 여기에 급여 담기
			
			System.out.print("기준이 될 금액 입력 : ");
			int input = sc.nextInt();
			sc.nextLine();
			
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY FROM EMPLOYEE WHERE SALARY > " + input;
			
			// 4. Statment 객체 생성
			stmt = conn.createStatement();
			
			// 5. Statment 객체를 이용하여 SQL 수행 후 결과 반환 받기
			// executeQuery() : SELECT 실행, ResultSet 반환
			// executeUpdate() : DML 실행, 결과 행의 개수 반환 (int)
			
			rs = stmt.executeQuery(sql);
			
			// 6. 조회 결과가 담겨있는 ResultSet을
			// 커서 이용해 1행씩 접근해
			// 각 행에 작성된 컬럼 값 얻어오기
			// -> while 안에서 꺼낸 데이터 출력
			
			while ( rs.next() ) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				String str = empId + " / " + empName + " / " + salary + "원";
				
				System.out.println(str);
				// 201 / 송중기 / 6000000원
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		} finally {
			// 7. 사용 완료된 JDBC 객체 자원 반환(close)
			// -> 생성된 역순으로 close!
			try {
				if(rs != null ) rs.close();
				if(stmt != null ) stmt.close();
				if(conn != null ) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
