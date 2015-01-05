package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserDao {
	
	//ID중복확인
	public User checkId(User userVO);
	
	//로그인
	public User login(User loginVO);
	
	//사용자 목록
	public List<User> getList();
	
	//사용자 등록
	public void add(User insertVO);
	
	//비밀번호 확인
	public User checkPassword(User userVO);
	
	//회원정보수정
	public void update(User updateVO);
	
	//회원 탈퇴
	public void delete(User deleteVO);
}
