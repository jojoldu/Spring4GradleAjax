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

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.vo.Common;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Repository
@Transactional
public class ReplyDaoImpl implements ReplyDao {

	@Autowired
	private SessionFactory sessionFactory = null;
	
	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	private Criteria getCriteria(){
		return getCurrentSession().createCriteria(User.class);
	}
	
	public ReplyDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReplyDaoImpl(SessionFactory SessionFactory) {
		super();
		this.sessionFactory = SessionFactory;
	}
	
	
	@Override
	public List<Reply> getList(Reply listVO) throws Exception {
		List list=null; 
		try{
			list=getCriteria().add(criterion) list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	
	@Override
	public int getPagingInfo(Common pagingVO) throws Exception {
		int result=0;
		try{
			result=getCurrentSession().createQuery("select count(no) from Reply").executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int add(Reply insertVO) throws Exception {
		try{
			getCurrentSession().save(insertVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int update(Reply updateVO) throws Exception {
		try{
			getCurrentSession().createQuery("update Reply set content = ? ,modifyDate=now(), imageName=? ")
							   .setString(0, updateVO.getContent())
							   .setString(1, updateVO.getImageName())
							   .executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getNo() throws Exception {
		int result=0;
		try{
			result=getCurrentSession().createQuery("select max(no) from Reply").executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public Reply getParent(int no) throws Exception {
		Reply result=null;
		try{
			result=(Reply)getCriteria().add(Restrictions.eq("no",no)).uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int delete(Reply deleteVO) throws Exception {
		int result=0;
		try{
			getCurrentSession().createQuery("delete from Reply where no = ?").setInteger(0, deleteVO.getNo());
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	

}
