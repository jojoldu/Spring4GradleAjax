package zum.potal.dwlee.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

public interface ReplyService {

	//댓글 목록
	public List<Reply> getList(PagingInfo pagingInfo);
	
	//댓글 페이징
	public PagingInfo getPagingInfo(PagingInfo pagingVO);
	
	
	//댓글 등록
	public void add(Reply insertVO, String path, MultipartFile mpf) ;
	
	//댓글 수정
	public void update(Reply updateVO, String path, MultipartFile mpf) ;
	
	//댓글 삭제
	public void delete(Reply deleteVO);
}
