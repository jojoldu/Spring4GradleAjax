package zum.potal.dwlee.service;

import java.util.List;

import zum.potal.dwlee.vo.CommonVO;
import zum.potal.dwlee.vo.ReplyVO;

public interface ReplyService {

	//댓글 목록
	public List<ReplyVO> getList(ReplyVO listVO) throws Exception;
	
	//댓글 페이징
	public CommonVO getPagingInfo(CommonVO pagingVO) throws Exception;
	
	public void makeInsertVO(ReplyVO insertVO) throws Exception;
	
	//댓글 등록
	public int add(ReplyVO insertVO, String path) throws Exception;
	
	//댓글 삭제
	public boolean delete(ReplyVO deleteVO) throws Exception;
}
