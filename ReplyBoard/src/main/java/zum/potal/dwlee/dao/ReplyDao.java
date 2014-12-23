package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.Common;
import zum.potal.dwlee.vo.Reply;

public interface ReplyDao {

	public List<Reply> getList(Reply listVO) throws Exception;
	
	public int getPagingInfo(Common pagingVO) throws Exception;
	
	public int add(Reply insertVO) throws Exception;
	
	public int update(Reply updateVO) throws Exception;
	
	public int getNo() throws Exception;
	
	public Reply getParent(int no) throws Exception;
	
	public int delete(Reply deleteVO) throws Exception;
}
