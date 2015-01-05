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
		Utils.setSecurityPassword(login);
		User result = userDao.login(login);
		return result;
	}

	@Override
	public List<User> getList() {
		return userDao.getList();
	}

	@Override
	public void add(User user)  {
		Utils.setSecurityPassword(user);
		userDao.add(user);
	}

	@Override
	public boolean checkPassword(User user){
	
		Utils.setSecurityPassword(user);
		return userDao.checkPassword(user) != null;
		
	}

	@Override
	public void update(User user) {
		Utils.setSecurityPassword(user);
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}
}