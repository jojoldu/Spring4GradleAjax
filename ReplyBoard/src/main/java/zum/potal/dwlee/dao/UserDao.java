package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.User;

public interface UserDao {
	
	//로그인
	public int login(User loginVO) throws Exception;
	
	//사용자 목록
	public List<User> getList() throws Exception;
	
	//사용자 등록
	public int add(User insertVO) throws Exception;
}
