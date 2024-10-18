package ehu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 부서명을 입력받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		// hint : SQL에서 문자열은 양쪽에 ''(홑따옴표) 필요
		
		Connection conn = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "kh_sdk", "kh1234");
			
			Scanner sc = new Scanner(System.in);
			System.out.print("조회할 부서명 : ");
			String input = sc.nextLine();
			
			String sql = "SELECT EMP_ID, EMP_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE, JOB_NAME " +
					"FROM EMPLOYEE E " +
					"LEFT JOIN DEPARTMENT D ON( E.DEPT_CODE = D.DEPT_ID ) " + 
					"JOIN JOB J ON(E.JOB_CODE = J.JOB_CODE) " +
					"WHERE DEPT_TITLE = '" + input + "' "
					+ "ORDER BY E.JOB_CODE"; 
			
			rs = conn.createStatement().executeQuery(sql);
			
			boolean flag = true;
			// 조회 결과 있다면 false, 없으면 true;
			
			while( rs.next() ) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.println(empId + " / " + empName + " / " + deptTitle + " / " + jobName);
			}
			if( flag) {
				System.out.println("일치하는 부서가 없습니다");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e ) {
				e.printStackTrace();
			}
		}
	}

}
