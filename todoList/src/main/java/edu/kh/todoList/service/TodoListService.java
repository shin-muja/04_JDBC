package edu.kh.todoList.service;

import java.util.Map;

import edu.kh.todoList.dto.Todo;

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

	/** 할 일 상세 조회 서비스
	 * @param todoNo
	 * @return null 또는 Todo
	 */
	Todo todoDetailView(int todoNo) throws Exception;

	/** 완료 여부 변경 서비스
	 * @param todoNo
	 * @return
	 */
	int todoComplete(int todoNo) throws Exception;

	/** 할 일 수정 서비스
	 * @param title
	 * @param detail
	 * @param todoNo
	 * @return
	 */
	int todoUpdate(String title, String detail, int todoNo) throws Exception;

	/** 할 일 삭제 서비스
	 * @param todoNo
	 * @return
	 */
	int todoDelete(int todoNo) throws Exception;
	
	
	
}
