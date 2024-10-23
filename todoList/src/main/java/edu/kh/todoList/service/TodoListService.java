package edu.kh.todoList.service;

import java.util.Map;

public interface TodoListService {

	/** 할 일 목록 반환 서비스
	 * @return todoList + 완료개수
	 */
	Map<String, Object> todoListFullView() throws Exception;

	/** 할 일 추가 서비스
	 * @param title
	 * @param detail
	 * @return int 성공 시 추가된 index 번호 / 실패 시 0 반환
	 */
	int todoAdd(String title, String detail) throws Exception;
	
	
	
}
