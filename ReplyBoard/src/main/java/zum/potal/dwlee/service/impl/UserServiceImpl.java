package zum.potal.dwlee.service.impl;

import java.util.List;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.utils.SeedKey;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	@Override
	public boolean checkDuplicateId(User user)  {
		User checkId = userDao.checkId(user);	
		return checkId == null;
	}
	
	@Override
	public User login(User login) {
		
		if(!Utils.setSecurityPassword(login)){
			return null;
		}
		return userDao.login(login);
	}

	@Override
	public List<User> getList() {
		return userDao.getList();
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
		
		return userDao.checkPassword(user) != null;
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
		userDao.delete(user);
	}
}