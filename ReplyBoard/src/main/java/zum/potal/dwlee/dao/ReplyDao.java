package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyDao {

	public List<Reply> getList(PagingInfo pagingInfo) throws Exception;
	
	public int getPagingInfo(PagingInfo pagingVO) throws Exception;
	
	public int add(Reply insertVO) throws Exception;
	
	public int update(Reply updateVO) throws Exception;
	
	public int getNo() throws Exception;
	
	public Reply getParent(int no) throws Exception;
	
	public int delete(Reply deleteVO) throws Exception;
}
