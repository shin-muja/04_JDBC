package edu.kh.todoList.dao;

import java.sql.Connection;
import java.util.List;

import edu.kh.todoList.dto.Todo;

public interface TodoListDAO {

	/** 할일 목록 전체 조회
	 * @param conn
	 * @return todoList
	 */
	List<Todo> todoListFullView(Connection conn) throws Exception;

	/** 완료된 할 일 개수 조회
	 * @param 완료된 개수
	 * @return
	 */
	int getCompleteCount(Connection conn) throws Exception;

	/** 할 일 추가
	 * @param conn
	 * @param title
	 * @param detail
	 * @return
	 */
	int todoAdd(Connection conn, String title, String detail) throws Exception;

}
