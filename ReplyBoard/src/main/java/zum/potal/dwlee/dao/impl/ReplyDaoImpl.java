package zum.potal.dwlee.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.vo.CommonVO;
import zum.potal.dwlee.vo.ReplyVO;

@Repository
public class ReplyDaoImpl implements ReplyDao {

	private SqlSessionFactory sqlSessionFactory = null;
	
	
	public ReplyDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReplyDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public List<ReplyVO> getList(ReplyVO listVO) throws Exception {
		List list=null; 
		SqlSession session = sqlSessionFactory.openSession();
		try{
			list=session.selectList("reply.getList", listVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	
	@Override
	public int getPagingInfo(CommonVO pagingVO) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		int result=0;
		try{
			result=session.selectOne("reply.getPagingInfo");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}

	@Override
	public int add(ReplyVO insertVO) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			session.insert("reply.add", insertVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return 0;
	}

	@Override
	public int getNo() throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		int result=0;
		try{
			result=session.selectOne("reply.getNo");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}
	
	@Override
	public ReplyVO getParent(int no) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		ReplyVO result=null;
		try{
			result=session.selectOne("reply.getParent", no);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}

	@Override
	public int delete(ReplyVO deleteVO) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		int result=0;
		try{
			result=session.delete("reply.delete", deleteVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;
	}
	
	

}
