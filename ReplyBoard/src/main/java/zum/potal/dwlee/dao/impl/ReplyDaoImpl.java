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
import org.springframework.transaction.annotation.Transactional;

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

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
		// TODO Auto-generated constructor stub
	}

	public ReplyDaoImpl(SessionFactory SessionFactory) {
		super();
		this.sessionFactory = SessionFactory;
	}


	@Override
	public List<Reply> getList(PagingInfo pagingInfo) throws Exception {
		List list=null; 
		list=getCriteria().addOrder(Order.desc("family"))
				.addOrder(Order.asc("depth"))
				.setFirstResult(pagingInfo.getFirstRow())
				.setMaxResults(pagingInfo.getPageSize())
				.list();
		return list;
	}


	@Override
	public int getPagingInfo(PagingInfo pagingVO) throws Exception {
		int result=0;
		result = ((Number)getCriteria().setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return result;
	}

	@Override
	public int add(Reply insertVO) throws Exception {
		getCurrentSession().save(insertVO);
		return 0;
	}

	@Override
	public int update(Reply updateVO) throws Exception {
		Reply originVO = (Reply)getCriteria().add(Restrictions.eq("no",updateVO.getNo())).uniqueResult();
		originVO.setContent(updateVO.getContent());
		originVO.setImageName(updateVO.getImageName());
		getCurrentSession().update(originVO);
		return 0;
	}

	@Override
	public int getNo() throws Exception {
		int result=0;
		Object obj = getCriteria().setProjection(Projections.max("no")).uniqueResult();
		if(obj != null){
			result = (Integer)obj;
		}
		return result;
	}

	@Override
	public Reply getParent(int no) throws Exception {
		Reply result=null;
		result=(Reply)getCriteria().add(Restrictions.eq("no",no)).uniqueResult();
		return result;
	}

	@Override
	public int delete(Reply deleteVO) throws Exception {
		int result=0;
		getCurrentSession().delete(deleteVO);
		return result;
	}



}
