package zum.potal.dwlee.service.impl;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public boolean login(User loginVO) throws Exception {
		boolean result=false;
		int checkId= userDao.login(loginVO);
		if(checkId>0){
			result=true;
		}
		return result;
	}

	@Override
	public List<User> getList() throws Exception {
		return userDao.getList();
	}

	@Override
	public int add(User insertVO) throws Exception {
		return userDao.add(insertVO);
	}

	
}
