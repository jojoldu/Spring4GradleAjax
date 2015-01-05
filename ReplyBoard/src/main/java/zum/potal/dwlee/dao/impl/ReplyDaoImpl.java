package zum.potal.dwlee.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

@Repository
public class ReplyDaoImpl implements ReplyDao {

	@Autowired
	private SessionFactory sessionFactory = null;

	protected Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	private Criteria getCriteria(){
		return getCurrentSession().createCriteria(Reply.class);
	}

	public ReplyDaoImpl() {
		super();
	}

	public ReplyDaoImpl(SessionFactory SessionFactory) {
		super();
		this.sessionFactory = SessionFactory;
	}


	@Override
	public List<Reply> getList(PagingInfo pagingInfo) {
		List list=null; 
		list=getCriteria()
				.addOrder(Order.desc("family"))
				.addOrder(Order.asc("path"))
				.setFirstResult(pagingInfo.getFirstRow())
				.setMaxResults(pagingInfo.getPageSize())
				.list();
		return list;
	}


	@Override
	public int getPagingInfo(PagingInfo pagingInfo){
		int result=0;
		result = ((Number)getCriteria().setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return result;
	}

	@Override
	public void add(Reply reply) {
		getCurrentSession().save(reply);
	}

	@Override
	public void update(Reply reply){
		Reply originVO = (Reply)getCriteria().add(Restrictions.eq("no",reply.getNo())).uniqueResult();
		originVO.setContent(reply.getContent());
		originVO.setImageName(reply.getImageName());
		getCurrentSession().update(originVO);
	}

	@Override
	public int getNo() {
		int result=0;
		Object obj = getCriteria().setProjection(Projections.max("no")).uniqueResult();
		if(obj != null){
			result = (Integer)obj;
		}
		return result;
	}

	@Override
	public Reply getParent(int no) {
		Reply result=null;
		result=(Reply)getCriteria().add(Restrictions.eq("no",no)).uniqueResult();
		return result;
	}

	@Override
	public void delete(Reply reply) {
		getCurrentSession().delete(reply);
	}



}
