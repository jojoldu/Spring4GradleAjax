package zum.potal.dwlee.service;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserService {

	//ID중복 확인
	public boolean checkDuplicateId(User userVO) throws Exception;
	
	//로그인
	public User login(User loginVO) throws Exception;
	
	//사용자목록 조회
	public List<User> getList() throws Exception;
	
	//회원가입
	public void add(User insertVO) throws Exception;
	
	//비밀번호 확인
	public boolean checkPassword(User userVO) throws Exception;
	
	//회원정보 수정
	public void update(User updateVO) throws Exception;
	
	//회원 탈퇴
	public void delete(User deleteVO) throws Exception;
}
