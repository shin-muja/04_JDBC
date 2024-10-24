package edu.kh.todoList.dao;

import static edu.kh.todoList.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.todoList.dto.Todo;

public class TodoListDAOImpl implements TodoListDAO {
	
	// JDBC 객체 참조변서 + Properties 참조 변수 선언
	// sql.xml 파일을 읽어올 때 쓰일 Properties
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public TodoListDAOImpl() {
		// TodoListDAOImpl 객체가 생성될 때
		// sql.xml 파일의 내용을 읽어와
		// Properties prop 객체에 K:V 세팅
		try {

			String filePath = TodoListDAOImpl.class.getResource("/xml/sql.xml").getPath();
			
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
			
		} catch(Exception e) {
			
			System.out.println("sql.xml 로드 중 예외 발생");
			e.printStackTrace();
			
		}
		
	}

	@Override
	public List<Todo> todoListFullView(Connection conn) throws Exception{
		
		// 결과 저장용 변수 선언
		List<Todo> todoList = new ArrayList<Todo>();
		
		
		
		try {
			String sql = prop.getProperty("todoListFullView");
			
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			
			while(rs.next() ) {

				// Builder 패턴 : 특정 값으로 초기화된 객체를 쉽게 만드는 방법
				// -> Lombok에서 제공하는 @Builder 어노테이션을 DTO에 작성해두면
				//    사용 가능한 패턴
				
				// rs.getInt("TODO_COMPLETE") == 1 : true 반환
				boolean complete = rs.getInt("TODO_COMPLETE") == 1;
				
				Todo todo = Todo.builder()
						.todoNo(rs.getInt("TODO_NO"))
						.title(rs.getString("TODO_TITLE"))
						.complete(complete)
						.regDate(rs.getString("REG_DATE"))
						.build();
				
				todoList.add(todo);
			}
			
		} finally {
			close(rs);
			close(stmt);
		}

		
		return todoList;
	}

	@Override
	public int getCompleteCount(Connection conn) throws Exception {
		
		int completeCount = 0;
		
		try {
			
			String sql = prop.getProperty("getCompleteCount");

			
			// 아직 파악하지 못한 메모리 구조 때문에
			// 이게 과연 끝나면 닫히는지 아닌지 모름
			// completeCount = conn.createStatement().executeUpdate(sql).getInt(1);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				completeCount = rs.getInt(1);
			}
			
			
		} finally {
			close(rs);
			close(stmt);
		}

		
		return completeCount;
	}

	@Override
	public int todoAdd(Connection conn, String title, String detail) throws Exception{
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("todoAdd");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public Todo todoDetailView(Connection conn, int todoNo) throws Exception {
		
		Todo todo = null;
		
		try {
			
			String sql = prop.getProperty("todoDetailView");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, todoNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boolean complete = rs.getInt("TODO_COMPLETE") == 1;
				
				todo = new Todo().builder()
						.todoNo(rs.getInt("TODO_NO"))
						.title(rs.getString("TODO_TITLE"))
						.detail(rs.getString("TODO_DETAIL"))
						.complete(complete)
						.regDate(rs.getString("REG_DATE"))
						.build();
			}
			
		} finally {
			close(rs);
			close(pstmt); 
		}
		
		
		return todo;
	}

	@Override
	public int todoComplete(Connection conn, int todoNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("todoComplete");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, todoNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	@Override
	public int todoUpdate(Connection conn, String title, String detail, int todoNo) throws Exception {
		
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("todoUpdate");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, title);
			pstmt.setString(2, detail);
			pstmt.setInt(3, todoNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int todoDelete(Connection conn, int todoNo) throws Exception {
		
		int result = 0;

		try {

			String sql = prop.getProperty("todoDelete");

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, todoNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
}
