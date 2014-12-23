package zum.potal.dwlee.service;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserService {

	//로그인
	public boolean login(User loginVO) throws Exception;
	
	//사용자목록 조회
	public List<User> getList() throws Exception;
	
	//회원가입
	public int add(User insertVO) throws Exception;
}
