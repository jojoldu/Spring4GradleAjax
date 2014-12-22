package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.ReplyVO;

public interface ReplyDao {

	public List<ReplyVO> getList(ReplyVO listVO) throws Exception;
	
	public int add(ReplyVO insertVO) throws Exception;
	
	public int getNo() throws Exception;
	
	public ReplyVO getParent(int no) throws Exception;
	
	public int delete(ReplyVO deleteVO) throws Exception;
}
