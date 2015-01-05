package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyDao {

	public List<Reply> getList(PagingInfo pagingInfo);
	
	public int getPagingInfo(PagingInfo pagingInfo);
	
	public void add(Reply reply);
	
	public void update(Reply reply);
	
	public int getNo();
	
	public Reply getParent(int no);
	
	public void delete(Reply reply);
}
