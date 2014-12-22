package zum.potal.dwlee.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	
	
	@Override
	@Transactional	
	public boolean login(UserVO loginVO) throws Exception {
		boolean result=false;
		int checkId= userDao.login(loginVO);
		if(checkId>0){
			result=true;
		}
		return result;
	}

	@Override
	@Transactional
	public List<UserVO> getList() throws Exception {
		return userDao.getList();
	}

	@Override
	@Transactional
	public int add(UserVO insertVO) throws Exception {
		return userDao.add(insertVO);
	}

	
}
