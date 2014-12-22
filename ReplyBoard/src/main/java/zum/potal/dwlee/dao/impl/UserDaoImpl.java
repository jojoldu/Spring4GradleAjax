package zum.potal.dwlee.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import zum.potal.dwlee.dao.UserDao;
import zum.potal.dwlee.vo.UserVO;

@Repository
public class UserDaoImpl implements UserDao {

	//Mybatis맵핑
	private SqlSessionFactory sqlSessionFactory = null;
	
	public UserDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super();
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public int login(UserVO loginVO) throws Exception {
		int checkLogin=0;
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			checkLogin=session.selectOne("user.login", loginVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return checkLogin;
	}

	@Override
	public List<UserVO> getList() throws Exception {
		List list = null;
		SqlSession session = sqlSessionFactory.openSession();
		
		try{
			list=session.selectList("user.getList");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public int add(UserVO insertVO) throws Exception {
		SqlSession session = sqlSessionFactory.openSession();
		try{
			session.insert("user.add", insertVO);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return 0;
	}

	
}
