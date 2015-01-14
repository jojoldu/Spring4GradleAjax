package zum.potal.dwlee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public boolean checkDuplicateId(String id)  {
		User checkId = userDao.getUser(id);	
		return checkId == null;
	}
	
	@Override
	public User getUser(User user) {
		
		if(!Utils.setSecurityPassword(user)){
			return null;
		}
		return userDao.getUser(user);
	}

	@Override
	public boolean add(User user)  {
		if(!Utils.setSecurityPassword(user)){
			return false;
		}
		userDao.add(user);
		return true;
	}

	@Override
	public boolean checkPassword(User user){
	
		if(!Utils.setSecurityPassword(user)){
			return false;
		}
		
		return userDao.getUser(user) != null;
	}

	@Override
	public boolean update(User user) {
		
		if(!Utils.setSecurityPassword(user)){
			return false;
		}
		
		userDao.update(user);
		return true;
	}

	@Override
	public void delete(User user) {
		user.setStatus('N');
		userDao.update(user);
	}
}