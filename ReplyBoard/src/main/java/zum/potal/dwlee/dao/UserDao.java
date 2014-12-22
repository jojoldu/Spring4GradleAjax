package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.UserVO;

public interface UserDao {
	
	//로그인
	public int login(UserVO loginVO) throws Exception;
	
	//사용자 목록
	public List<UserVO> getList() throws Exception;
	
	//사용자 등록
	public int add(UserVO insertVO) throws Exception;
}
