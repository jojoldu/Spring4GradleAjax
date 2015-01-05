package zum.potal.dwlee.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	@Transactional
	public List<Reply> getList(PagingInfo pagingInfo) {
		return replyDao.getList(pagingInfo);
	}


	@Override
	@Transactional	
	public PagingInfo getPagingInfo(PagingInfo pagingVO) {
		int totalRow = replyDao.getPagingInfo(pagingVO);
		int totalPageCount=totalRow/pagingVO.getPageSize();		
		if(totalRow%pagingVO.getPageSize() !=0){
			totalPageCount +=1; 
		}
		pagingVO.setTotalPageCount(totalPageCount);
		return pagingVO;
	}



	private void makeInsertVO(Reply insertVO){
		String no = String.valueOf(insertVO.getNo());
		int zeroLength = 5-no.length();//자릿수
		StringBuffer path = new StringBuffer("0");

		for(int i=0;i<zeroLength;i++){
			path.append("0");
		}
		path.append(no);
		insertVO.setPath(path.toString());

		if(insertVO.getParent() == 0){//부모코드가 없는경우, 답글이 아닌 일반글

			insertVO.setFamily(insertVO.getNo());

		}else{//부모코드가 있는경우, 해당 부모코드의 familyCode와 depth를 사용한다

			Reply parentVO = replyDao.getParent(insertVO.getParent());
			insertVO.setFamily(parentVO.getFamily());
			insertVO.setDepth(parentVO.getDepth()+1);

		}
	}

	private void imageDownload(Reply replyVO, String path, MultipartFile mpf){
		MultipartFile image = mpf;
		try{
			if(image!=null){
				String originName = image.getOriginalFilename();
				String imgExt = originName.substring(originName.lastIndexOf(".")+1, originName.length());

				if(imgExt.equalsIgnoreCase("JPG") || imgExt.equalsIgnoreCase("PNG") || imgExt.equalsIgnoreCase("GIF")){//jps, png, gif만 허용
					String fileName = String.valueOf(replyVO.getNo());
					File dir = new File(path);
					if(!dir.exists()){
						dir.mkdirs();
					}
					String imageName=fileName+"."+imgExt;
					File file = new File(path +File.separatorChar+ imageName);
					image.transferTo(file);
					replyVO.setImageName(imageName);
				}
			}
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void add(Reply insertVO, String path, MultipartFile mpf){
		int no = replyDao.getNo()+1;
		insertVO.setNo(no);

		makeInsertVO(insertVO);

		imageDownload(insertVO,path,mpf);

		replyDao.add(insertVO);
	}

	@Override
	@Transactional
	public void update(Reply updateVO, String path, MultipartFile mpf) {
		imageDownload(updateVO,path,mpf);
		replyDao.update(updateVO);
	}


	@Override
	@Transactional			
	public void delete(Reply deleteVO) {
		replyDao.delete(deleteVO);
	}

}
