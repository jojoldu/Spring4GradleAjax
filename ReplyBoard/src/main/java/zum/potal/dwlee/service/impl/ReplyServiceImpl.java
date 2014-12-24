package zum.potal.dwlee.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDao replyDao;
	
	//private final String FILE_PATH="/";
	
	@Override
	public List<Reply> getList(PagingInfo pagingInfo) throws Exception {
		return replyDao.getList(pagingInfo);
	}
	
	
	@Override
	public PagingInfo getPagingInfo(PagingInfo pagingVO) throws Exception {
		int totalRow = replyDao.getPagingInfo(pagingVO);
		int totalPageCount=totalRow/pagingVO.getPageSize();		
		if(totalRow%pagingVO.getPageSize() !=0){
			totalPageCount +=1; 
		}
		pagingVO.setTotalPageCount(totalPageCount);
		return pagingVO;
	}



	@Override
	public void makeInsertVO(Reply insertVO) throws Exception{
		if(insertVO.getParent()==0){//부모코드가 없는경우, 답글이 아닌 일반글
			insertVO.setFamily(insertVO.getNo());
		}else{//부모코드가 있는경우, 해당 부모코드의 familyCode와 depth를 사용한다
			Reply parentVO = replyDao.getParent(insertVO.getParent());
			insertVO.setFamily(parentVO.getFamily());
			insertVO.setDepth(parentVO.getDepth()+1);
		}
	}
	
	
	@Override
	public int add(Reply insertVO, String path, MultipartFile mpf) throws Exception {
		int no = replyDao.getNo()+1;
		insertVO.setNo(no);
		makeInsertVO(insertVO);
		
		MultipartFile image = mpf;
		if(image!=null){
			String originName = image.getOriginalFilename();
			String imgExt = originName.substring(originName.lastIndexOf(".")+1, originName.length());
			
	        if(imgExt.equalsIgnoreCase("JPG") || imgExt.equalsIgnoreCase("PNG") || imgExt.equalsIgnoreCase("GIF")){//jps, png, gif만 허용
	        	String fileName = String.valueOf(no);
	        	File dir = new File(path);
	        	if(!dir.exists()){
	        		dir.mkdirs();
	        	}
	        	String imageName=fileName+"."+imgExt;
	        	File file = new File(path + imageName);
                image.transferTo(file);
                insertVO.setImageName(imageName);
	        }
		}
		
		return replyDao.add(insertVO);
	}

	@Override
	public int update(Reply updateVO, String path, MultipartFile mpf) throws Exception {
		int no = replyDao.getNo()+1;
		updateVO.setNo(no);
		makeInsertVO(updateVO);
		
		MultipartFile image = mpf;
		if(image!=null){
			String originName = image.getOriginalFilename();
			String imgExt = originName.substring(originName.lastIndexOf(".")+1, originName.length());
			
	        if(imgExt.equalsIgnoreCase("JPG") || imgExt.equalsIgnoreCase("PNG") || imgExt.equalsIgnoreCase("GIF")){//jps, png, gif만 허용
	        	String fileName = String.valueOf(no);
	        	File dir = new File(path);
	        	if(!dir.exists()){
	        		dir.mkdirs();
	        	}
	        	String imageName=fileName+"."+imgExt;
	        	File file = new File(path + imageName);
                image.transferTo(file);
                updateVO.setImageName(imageName);
	        }
		}
		
		return replyDao.add(updateVO);
	}


	@Override
	public boolean delete(Reply deleteVO) throws Exception {
		replyDao.delete(deleteVO);
		return false;
	}
	
	

}
