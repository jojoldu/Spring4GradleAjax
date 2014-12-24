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
		// TODO Auto-generated constructor stub
	}

	public UserDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}


	@Override
	public User checkId(User userVO) throws Exception {
		User result=null;
		result=(User)getCriteria().add(Restrictions.eq("id", userVO.getId())).uniqueResult();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int login(User loginVO) throws Exception {
		int result=1;
		List check = getCriteria().add(Restrictions.like("id", loginVO.getId(), MatchMode.ANYWHERE))
				.add(Restrictions.like("password", loginVO.getPassword(), MatchMode.ANYWHERE))
				.list();
		result=check.size();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")	
	public List<User> getList() throws Exception {
		List list=null;
		list = getCriteria().list();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int add(User insertVO) throws Exception {
		getCurrentSession().save(insertVO);
		return 0;
	}


}
