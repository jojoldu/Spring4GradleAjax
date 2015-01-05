package zum.potal.dwlee.dao;

import java.util.List;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyDao {

	public List<Reply> getList(PagingInfo pagingInfo);
	
	public int getPagingInfo(PagingInfo pagingVO);
	
	public void add(Reply insertVO);
	
	public void update(Reply updateVO);
	
	public int getNo();
	
	public Reply getParent(int no);
	
	public void delete(Reply deleteVO);
}
