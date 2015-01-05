package zum.potal.dwlee.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory = null;

	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	private Criteria getCriteria(){
		return getCurrentSession().createCriteria(User.class);
	}

	public UserDaoImpl() {
		super();
	}

	public UserDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}


	@Override
	public User checkId(User userVO){
		User result=null;
		result=(User)getCriteria().add(Restrictions.eq("id", userVO.getId())).uniqueResult();
		return result;
	}

	@Override
	public User login(User loginVO){
		User result=null;
		result=(User)getCriteria().add(Restrictions.eq("id",loginVO.getId()))
								  .add(Restrictions.eq("password", loginVO.getPassword()))
								  .uniqueResult();
		return result;
	}

	@Override
	public List<User> getList(){
		return getCriteria().list();
	}

	@Override
	public void add(User insertVO){
		getCurrentSession().save(insertVO);
	}

	@Override
	public User checkPassword(User userVO){
		User result=null;
		result=(User)getCriteria().add(Restrictions.eq("id",userVO.getId()))
								  .add(Restrictions.eq("password", userVO.getPassword()))
								  .uniqueResult();
		return result;	
	}

	@Override
	public void update(User updateVO){
		getCurrentSession().update(updateVO);
	}

	@Override
	public void delete(User deleteVO){
		getCurrentSession().delete(deleteVO);
	}
}
