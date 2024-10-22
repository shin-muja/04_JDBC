package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.dto.User;
import edu.kh.jdbc.sevice.UserService;

public class UserView {
	
	// 필드
	private Scanner sc = new Scanner(System.in);
	private UserService service = new UserService();

	/**
	 * JDBCTemplate 사용 테스트
	 */
	public void test() {
		
		// 입력된 ID와 일치하는 USER 정보 조회
		System.out.print("ID 입력 : ");
		String input = sc.nextLine();
		
		// 서비스 호출 후 결과 반환 받기
		User user = service.selectId(input);
		
		System.out.println(user);
		
	}

	public void mainMenu() {

		int input = 0;
		
		do {
			try {
				
				System.out.println("\n===== User 관리 프로그램 =====\n");
				System.out.println("1. User 등록(INSERT)");
				System.out.println("2. User 전체 조회(SELECT)");
				System.out.println("3. User 중 이름에 검색어가 포함된 회원 조회 (SELECT)");
				System.out.println("4. USER_NO를 입력 받아 일치하는 User 조회(SELECT)");
				System.out.println("5. USER_NO를 입력 받아 일치하는 User 삭제(DELETE)");
				System.out.println("6. ID, PW가 일치하는 회원이 있을 경우 이름 수정(UPDATE)");
				System.out.println("7. User 등록(아이디 중복 검사)");
				System.out.println("8. 여러 User 등록하기");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("메뉴 선택 : ");
				input = sc.nextInt();
				sc.nextLine(); // 버퍼에 남은 개행문자 제거
				
				switch(input) {
				case 1: insertUser(); break;
				case 2: selectAll(); break;
				case 3: selectName(); break;
				case 4: selectUser(); break;
				case 5: deleteUser(); break;
				case 6: updateName(); break;
				case 7: insertUser2(); break;
				case 8: multiInsertUser(); break;
				
				case 0 : System.out.println("\n[프로그램 종료]\n"); break;
				default: System.out.println("\n[메뉴 번호만 입력하세요]\n");
				}
				
				System.out.println("\n-------------------------------------\n");
				
			} catch (InputMismatchException e) {
				// Scanner를 이용한 입력 시 자료형이 잘못된 경우
				System.out.println("\n***잘못 입력 하셨습니다***\n");
				
				input = -1; // 잘못 입력해서 while문 멈추는걸 방지
				sc.nextLine(); // 입력 버퍼에 남아있는 잘못된 문자 제거
				
			} catch (Exception e) {
				// 발생되는 예외를 모두 해당 catch 구문으로 모아서 처리
				e.printStackTrace();
			}
			
		}while(input != 0);

		
	} //mainMenu()정렬
	
	

	/**
	 * 1. 아이디, 패스워드, 이름을 입력 받은후 DB에 INSERT
	 */
	public void insertUser() throws Exception{
		
		System.out.println("\n===== 1. User 등록=====\n");
		
		System.out.print(("iD : "));
		String userId = sc.next();
		
		System.out.print(("passwor : "));
		String userpass = sc.next();
		
		System.out.print(("Name : "));
		String userName = sc.next();
		
		// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
		// User DTO 객체를 생성한 후 필드에 값을 세팅
		User user  = new User();
		
		// setter 이용
		user.setUserId(userId);
		user.setUserPw(userpass);
		user.setUserName(userName);
		
		// 서비스 호출(INSERT) 후 결과(int, 삽입된 행의 개수) 반환 받기
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		
		if(result > 0 ) {
			System.out.println("\n" + userId + " 사용자가 등록되었습니다.\n");
		} else {
			System.out.println("\n*****등록 실패*****\n");			
		}
		
	}
	
	/**
	 * 2. User 전체 조회(SELECT)
	 */
	public void selectAll() throws Exception{
		
		System.out.println("\n=== 2. User 전체 조회 (SELECT) ===\n");
		
		// 서비스 호출(SELECT) 후 결과(List<User>) 반환 받기
		List<User> userList = service.selectAll();
		
		// 조회 결과가 없을 경우 
		if( userList.isEmpty()) {
			
			System.out.println("\n\n***** 조회 결과가 없습니다 *****\n");
			return;
			
		} 
	
		// 있을 경우 향상된 for문 이용해서 userList에 있는 User 객체 출력
		for(User user : userList) {
			System.out.println(user);
		}
		
			
		
	}


	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회(SELECT)
	 */
	private void selectName() throws Exception{
		
		System.out.println("\n\n***** 이름 검색어 조회 *****\n");
		
		System.out.print("검색할 이름 입력 : ");
		String input = sc.nextLine();
		
		System.out.println();
		System.out.println();
		
		List<User> userList = service.selectName(input);
		
		if( userList.isEmpty() ) {
			System.out.println("일치하는 이름을 가진 USER가 존재하지 않습니다.");
			return;
		}
		
		for(User user : userList) {
			System.out.println(user);
		}
		
	}
	
	/**
	 * 4. USER_NO를 입력 받아 일치하는 User 조회
	 */
	private void selectUser() throws Exception{
		
		System.out.println("\n\n***** USER_NO를 입력 받아 일치하는 User 조회 ******\n");
		
		System.out.print("검색할 USER_NO 입력 : ");
		int input = sc.nextInt();
		sc.nextLine();
		
		User user = service.selectUser(input);
		
		if( user == null ) {
			System.out.println("일치하는 USER가 존재하지 않습니다");
			return;
		}
		
		System.out.println(user);
		
	}
	
	/**
	 * 5. USER_NO를 입력 받아 User 삭제
	 */
	private void deleteUser() throws Exception {
		System.out.println("\n\n***** USER_NO를 입력 받아 User 삭제 ******\n");
		
		System.out.print("삭제할 USER_NO 입력 : ");
		int input = sc.nextInt();
		sc.nextLine();
		
		int result = service.deleteUser(input);
		
		if( result >  0) {
			System.out.println("USER_NO : " + input + " 삭제 성공");
			return;
		}

		System.out.println("일치하는 USER가 존재하지 않습니다");
		
	}

	/**
	 * 6. ID, PW가 일치하는 회원이(SELECT) 있을 경우 이름수정(UPDATE)
	 * 
	 */
	private void updateName() throws Exception{
		
		System.out.println("\n\n***** USER 이름 수정 *****\n");
		
		System.out.print("아이디 입력 : ");
		String inputId = sc.nextLine();
		
		System.out.print("비밀번호 입력 : ");
		String str = sc.nextLine();
		
		// 입력받은 ID, PW 가 일치하는 회원이 존재하는지 조회
		// -> USER_NO 조회
		int userNo = service.selUpdateName(inputId, str);
		
		if( userNo == 0) { // 조회된 결과 이름
			System.out.println("\n아이디, 비밀번호가 일치하는 사용자가 없습니다.");
			return;
		}
		
		System.out.println();
		System.out.print("\n수정할 이름 입력 : ");
		str = sc.nextLine();
		
		boolean result = service.updateName(str, userNo);
		
		if( result ) System.out.println("\n수정 성공\n\n");
		else System.out.println("\n수정 실패\n\n");	
		
	}
	
	
	/**
	 * 7. User 등록 검사(ID 중복검사)
	 */
	private void insertUser2() throws Exception{
		
		System.out.println("\n\n=== 7. User 등록 검사(ID 중복검사) ===\n");
		

		String userId = null; // 입력된 아이디를 저장할 변수
		
		while ( true) {		
			System.out.print("ID 입력 : ");
			userId = sc.nextLine();
			
			// 입력 받은 userId가 중복인지 검사하는
			// 서비스 호출 후
			// 결과(int, 중복 == 1, 아니면 == 0) 반환 받기
			int count = service.idCheck(userId); 
			
			if( count == 0 ) {
				System.out.println("사용가능한 아이디 입니다. ");
				break;
			}
			
			System.out.println("중복되는 아이디 입니다. 다시 입력해주세요");
		}
		
		// 아이디가 중복이 아닌경우 while 종료 후
		System.out.print("PW 입력 : ");
		String userPw = sc.nextLine();
		
		System.out.print("이름 입력 : ");
		String userName = sc.nextLine();
		
		// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
		// User DTO 객체를 생성한 후 필드에 값을 세팅
		User user  = new User();
		
		// setter 이용
		user.setUserId(userId);
		user.setUserPw(userPw);
		user.setUserName(userName);
		
		// 1번에서 만든 service 메서드 재활용
		// 서비스 호출(INSERT) 후 결과(int, 삽입된 행의 개수) 반환 받기
		int result = service.insertUser(user);
		
		// 반환된 결과에 따라 출력할 내용 선택
		
		if(result > 0 ) {
			System.out.println("\n" + userId + " 사용자가 등록되었습니다.\n");
		} else {
			System.out.println("\n*****등록 실패*****\n");			
		}
		
	}

	/**
	 * 8. 여러 User 등록하기
	 */
	private void multiInsertUser() throws Exception{
		
		/* 등록할 User 수 : 2
		 * 
		 * 1번째 userId : user100
		 * -> 사용가능한 ID 입니다
		 * 1번째 userPw : pass100
		 * 1번째 userName : 유저백
		 * ----------------------------
		 * 2번째 userId : user200
		 * -> 사용가능한 ID 입니다.
		 * 2번째 userPw : pass200
		 * 2번째 userName : 유저이백
		 * 
		 * -- 전체 삽입 성공 / 삽입 실패
		 */
		
		System.out.println("\n=== 8. 여러 User 등록하기 === \n");
		
		System.out.print("등록할 User 수 : ");
		int input = sc.nextInt();
		sc.nextLine(); // 버퍼 개행문자 제거
		
		// 입력받은 회원 정보를 저장할 List 객체 생성
		List<User> userList = new ArrayList<User>();
		
		for( int i = 0 ; i < input ; i++ ) {
			
			String userId = null; // 입력된 아이디를 저장할 변수
			
			while ( true) {		
				System.out.print((i + 1) + "번째 User ID 입력 : ");
				userId = sc.nextLine();
				
				int count = service.idCheck(userId); 
				
				if( count == 0 ) {
					System.out.println("사용가능한 아이디 입니다. ");
					break;
				}
				
				System.out.println("중복되는 아이디 입니다. 다시 입력해주세요");
			}
			
			// 아이디가 중복이 아닌경우 while 종료 후
			System.out.print((i + 1) + "번째 User PW 입력 : ");
			String userPw = sc.nextLine();
			
			System.out.print((i + 1) + "번째 User 이름 입력 : ");
			String userName = sc.nextLine();
			
			System.out.println("---------------------------------");
			
			// 입력받은 값 3개를 한번에 묶어서 전달할 수 있도록
			// User DTO 객체를 생성한 후 필드에 값을 세팅
			User user  = new User();
			
			// setter 이용
			user.setUserId(userId);
			user.setUserPw(userPw);
			user.setUserName(userName);
			
			//userList에 user 추가
			userList.add(user);
			
		} // for문 종료
		
		// 입력 받은모든 사용자를 INSERT하는 서비스 호출
		// -> 결과로 삽입된 행의 개수 반환
		
		int result = service.multiInsertUser(userList);
		
		// 전체 삽입 성공 시
		if( result == userList.size() ) {
			System.out.println("전체 삽입 성공");
		} else {

			System.out.println("삽입 실패");
		}
		
		System.out.println();
		
		
	}
}











