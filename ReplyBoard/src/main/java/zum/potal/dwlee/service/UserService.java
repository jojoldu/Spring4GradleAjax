package zum.potal.dwlee.service;

import java.util.List;

import zum.potal.dwlee.vo.UserVO;

public interface UserService {

	//로그인
	public boolean login(UserVO loginVO) throws Exception;
	
	//사용자목록 조회
	public List<UserVO> getList() throws Exception;
	
	//회원가입
	public int add(UserVO insertVO) throws Exception;
}
