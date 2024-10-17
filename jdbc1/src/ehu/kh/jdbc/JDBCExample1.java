package ehu.kh.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {
	
	public static void main(String[] args) {
		
		/*
		 * JDBC(Java DataBase Connectivity)
		 * 
		 * - Java에서 DB에 연결 할 수 있게 해주는
		 * Java API(Java에서 제공하는 코드)
		 * 
		 * -> java.sql 패키지에 존재함
		 * 
		 * */
		
		// Java 코드를 이요해
		// EMPLOYEE 테이블에서
		// 사번, 이름, 부서코드, 직급고트, 급여, 입사일 조회 후
		// 이클립스 콘솔에 출력
		
		/* 1. JDBC 객체 참조용 변수 선언 */
		
		// java.sqpl.Connection
		// 특정 DBMS와 연결하기 위한 정보를 저장한 객체
		// == DBeaber에서 사용하는 DB 연결과 같은 역할의 객체
		//	  (DB 서버 주소, 포트번호, DB 이름, 계정명, 비밀번호
		Connection conn = null;
		
		// java.sql.Statement
		// - 1) SQL을 java -> DB에 전달
		// - 2) DB에서 SQL 수행 결과를 반환 받아옴 (DB -> Java)
		Statement stmt = null;
		
		// java.sql.ResultSet
		// - SELECT 조회 결과를 저장하는 객체
		ResultSet rs = null;
		
		try {
			
			/* 2. Driver Manager 객체를 이요해서 Connection 객체 생성하기 */
			
			// java.sql.DriverManager
			// - DB 연결 정보와 JDBC 드라이버를 이요해서
			//   원하는 DB와 연결할 수 있는 Connection 객체를 생성하는 객체
			
			// 2-1 ) Oracle JDBC Driver 객체를 메모리에 로드(적재) 하기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Unhandled exception type ClassNotFoundException
			// 외부 패키지명을 칠때 오타가 나거나 없는 패키지 일수 있기에
			// 나올수 있는 예외
			
			// Class.forName("패키지명 + 클래스명");
			// - 해당 클래스를 읽어 메모리에 적재
			// -> JVM이 프로그램 독장에 사용할 객체를 생성
			
			// oracle.jdbc.driver.OracleDirver
			// - Oracle DBMS 연결 시 필요한 코드가 답겨있는 클래스
			//   Oracle 에서 만들어서 준 클래스
			
			// 2-2 ) DB 연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			
			String host = "localhost"; // DB 서버 컴퓨터의 IP 또는 도메인 주소
									   // localhost == 현재 컴퓨터
			
			String port = ":1521"; // 프로그램 연결을 위한 port 번호
			
			String dbName = ":XE"; // DBMS 이름 (XE == eXpress Edition)
			// jdbc:oracle:thin:@localhost:1521:XE
			
			String userName = "kh_sdk"; // 사용자 계정명
			
			String password = "kh1234"; // 계정 비밀번호
			
			// 2-3) DB 연결 정보와 DriverManager를 이용해서 Connection 객체 생성
			conn = DriverManager.getConnection(type + host + port + dbName,
					userName, password);
			
			// Connection 객체가 잘 생성되었는지 확인(객체 주소 반환)
			// == DB 연결정보에 오타가 없는지 확인
			// -> DB 연결 정보가 잘못되거나 객체 생성에 문제가 생기면 SQLException 발생
			System.out.println(conn); // oracle.jdbc.driver.T4CConnection@397fbdb
			
			/* 3. SQL 작성 */
			// !! 주의 사항 !!
			// -> JDBC 코드에서 SQL 작성 시
			//    세미콜런(;) 을 작성하면 안된다
			// -> "sql 명령어가 올바르게 종료되지 않았습니다" 예외 발생
			
			// Java에서 작성시 오타를 알려주지를 않으니 DBeaver에 작성하여 확인 후
			// 적으면 좋다. 또한 길어서 내려 쓸때 공백을 헷갈려서 지울 수 잇으니 주의
			String sql = "SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			// 연결된 DB에 SQL을 전달하고 결과를 반환 받을
			// Statement 객체를 생성
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			// 1) ResultSet Statment.executeQuery(sql);
			// -> sql이 SELECT문일 때 결과로 java.sql.ResultSet 반환
			
			// 2) int Statment.executeUpdate(sql);
			// -> DML(INSERT, UPDATE, DELETE) 실행 메서드
			//    결과로 int 반환(삽입, 수정, 삭제된 행의 개수)
			
			rs = stmt.executeQuery(sql);
			
			/* 6. 조회 결과가 담겨있는 Result Set 을
			 * 커서(Cursor)를 이용해
			 * 1행 씩 접근해서 각 행에 작성된 컬럼 값 얻어오기
			 */
			
			// rs.next() : 커서를 다음 행으로 이동 시킨 후
			// 		이동된 행에 값이 있으면 true, 없으면 false 반환
			//		맨 처음 호출 시 1행부터 시작
			
			while (rs.next()) {
				// rs.get자료형(컬럼명 | 순서);
				// - 현재 행에서 지정된 컬럼의 값을 얻어와 반환
				// -> 지정된 자료형 형태로 값이 반환됨
				// (자료형을 잘못 지정하면 예외 발생)
				
				// [java]				[db]
				// String		  	CHAR, VARCHAR2
				// int, long	  	NUMBER (정수만 저장된 컬럼)
				// float, double  	NUMBER (정수 + 실수)
				// java.sql.Date  	DATE
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_CODE");
				String jobCode = rs.getString("JOB_CODE");
				int salary = rs.getInt("SALARY");
				Date date = rs.getDate("HIRE_DATE");
				
				String str = String.format("사번 : %s, 이름: %s" +
									", 부서코드 : %s, 직급코드 : %s, 급여 : %d, 입사일 : %s", empId, empName,
									deptCode, jobCode, salary, date.toString());
				
				System.out.println(str);
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("해당 Class를 찾을 수 없습니다.");
		} catch (SQLException e) {
				// SEQExcoption :
				// DB 연결과 관련된 모든 예외의 최상위 부모
			e.printStackTrace();
		} finally {
			
			/* 7. 사용 안뢰된 JDBC 객체 자원 반환(close) */
			// -> 수행하지 않으면 DB와 연결된 Connection이 그대로 남아 있어서
			//    다른 클라이언트가 추가적으로 연결되지 못하는
			//    문제가 발생할 수 있다
			
			
			// 메모리 반환 실행 시 만들어진 역순으로 닫듣 것을 추천
			try {
				if( rs != null ) rs.close();
				if( stmt != null ) stmt.close();
				if( conn != null ) conn.close();
				
				// if문은 NullPointException 방지용 구문
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}
	
}





















