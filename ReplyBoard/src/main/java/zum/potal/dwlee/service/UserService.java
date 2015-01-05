package zum.potal.dwlee.service;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserService {

	//ID중복 확인
	public boolean checkDuplicateId(User user);
	
	//로그인
	public User login(User login);
	
	//사용자목록 조회
	public List<User> getList();
	
	//회원가입
	public void add(User insert);
	
	//비밀번호 확인
	public boolean checkPassword(User user);
	
	//회원정보 수정
	public void update(User user);
	
	//회원 탈퇴
	public void delete(User user);
}
