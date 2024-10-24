package edu.kh.todoList.controller;

import java.io.IOException;

import edu.kh.todoList.dto.Todo;
import edu.kh.todoList.service.TodoListService;
import edu.kh.todoList.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 할 일 상세 조회 요청처리 Servlet
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet {

	// a태그 요청은 GET 방식
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		try {
			
			// 요청 시 전달 받은 파라미터 얻어오기
			int todoNo = Integer.parseInt( req.getParameter("todoNo"));
			
			// 서비스 객체 생성
			TodoListService service = new TodoListServiceImpl();
			
			Todo todo = service.todoDetailView(todoNo);
			// TODO_NO 컬럼값이 todoNo와 같은 todo가 없으면 null 반환 / 있으면 Todo 객체
			
			// todo가 존재하지 않을 경우
			// -> 메인페이지(/) redirect 후
			// "할 일이 존재하지 않습니다"
			// alert 출력
			if( todo == null ) {
				HttpSession session = req.getSession();
				
				session.setAttribute("message", "할 일이 존재하지 않습니다.");
				
				resp.sendRedirect("/");
				
				return;
			}
			
			// todo가 존재하는 경우
			// detail.jsp로 forward해서 응답
			req.setAttribute("todo", todo);
			
			// JSP 파일 경로 (webapp 폴더 기준으로 작성)
			req.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
