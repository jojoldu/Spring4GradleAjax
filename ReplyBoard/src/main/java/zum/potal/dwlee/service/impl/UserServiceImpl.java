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
	public boolean checkId(User loginVO) throws Exception {
		User checkId = userDao.checkId(loginVO);	
		return checkId == null;
	}
	
	@Override
	public User login(User loginVO) throws Exception {
		Utils.setSecurityPassword(loginVO);
		User result = userDao.login(loginVO);
		return result;
	}

	@Override
	public List<User> getList() throws Exception {
		return userDao.getList();
	}

	@Override
	public void add(User insertVO) throws Exception {
		Utils.setSecurityPassword(insertVO);
		userDao.add(insertVO);
	}

	@Override
	public boolean checkPassword(User userVO) throws Exception {
	
		Utils.setSecurityPassword(userVO);
		return userDao.checkPassword(userVO) != null;
		
	}

	@Override
	public void update(User updateVO) throws Exception {
		Utils.setSecurityPassword(updateVO);
		userDao.update(updateVO);
	}

	@Override
	public void delete(User deleteVO) throws Exception {
		userDao.delete(deleteVO);
	}
}