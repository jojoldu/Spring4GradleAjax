package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserDao {
	
	//ID중복확인
	public User checkId(User userVO) throws Exception;
	
	//로그인
	public User login(User loginVO) throws Exception;
	
	//사용자 목록
	public List<User> getList() throws Exception;
	
	//사용자 등록
	public void add(User insertVO) throws Exception;
	
	//비밀번호 확인
	public User checkPassword(User userVO) throws Exception;
	
	//회원정보수정
	public void update(User updateVO) throws Exception;
	
	//회원 탈퇴
	public void delete(User deleteVO) throws Exception;
}
