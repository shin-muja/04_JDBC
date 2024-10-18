package ehu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JDBCExample7 {
	public static void main(String[] args) {
		
		// EMPLOYEE	테이블에서
		// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
		// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것
		
		// - 조건 1 : 성별 (M, F)
		// - 조건 2 : 급여 범위
		// - 조건 3 : 급여 오름차순/내림차순
		
		// [실행화면]
		// 조회할 성별(M/F) : F
		// 급여 범위(최소, 최대 순서로 작성) : 3000000 4000000
		// 급여 정렬(1.ASC, 2.DESC) : 2
		
		// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
		//--------------------------------------------------------
		// 218  | 이오리 | F    | 3890000 | 사원   | 없음
		// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
		// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
		// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
		// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
		// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String userName = "kh_sdk";
			String password = "kh1234";
			
			conn = DriverManager.getConnection(url, userName, password);
			
			Scanner sc = new Scanner(System.in);
			System.out.print("조회할 성별(M/F) : ");
			String gender = sc.nextLine().toUpperCase();
			
			if( gender.length() > 1) return;
			 
			System.out.print("급여 범위(최소, 최대 순서로 작성) : ");
			int min = sc.nextInt();
			int max = sc.nextInt();
			
			System.out.println("급여정렬(1.ASC, 2.DESC) : ");
			int orderBy = sc.nextInt();
			String order = null;
			if(orderBy == 1) {
				order = "ASC";
			} else {
				order = "DESC";
			}
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, SALARY, DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') GENDER,
					JOB_NAME, NVL(DEPT_TITLE, '없음') DEPT
					FROM EMPLOYEE E
					LEFT JOIN JOB J ON(E.JOB_CODE = J.JOB_CODE)
					LEFT JOIN DEPARTMENT D ON (DEPT_CODE = DEPT_ID)
					WHERE DECODE(SUBSTR(EMP_NO, 8, 1), '1', 'M', '2', 'F') = ?
					AND SALARY BETWEEN ? AND ?
					ORDER BY 3 
					""" + order;
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, gender);
			pstmt.setInt(2, min);
			pstmt.setInt(3, max);
			
			rs = pstmt.executeQuery();
			
			boolean flag = true;
			
			System.out.println("사번 | 이름   | 성별 | 급여    | 직급명 | 부서명");
			System.out.println("--------------------------------------------------------");
			
			while(rs.next()) {
				flag = false;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String sGender = rs.getString("GENDER");
				int salary = rs.getInt("SALARY");
				String jobName = rs.getString("JOB_NAME");
				String dept = rs.getString("DEPT");
				

				System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
				empId, empName, sGender, salary, jobName, dept);
			}
			
			if( flag ) {
				System.out.println("일치하는 정보가 없습니다");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
				
		
	}
}
