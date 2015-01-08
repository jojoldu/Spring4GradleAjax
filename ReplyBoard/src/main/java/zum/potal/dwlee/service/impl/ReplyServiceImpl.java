package zum.potal.dwlee.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import zum.potal.dwlee.dao.ReplyDao;
import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@Override
	@Transactional
	public List<Reply> getList(PagingInfo pagingInfo) {
		return replyDao.getList(pagingInfo);
	}


	@Override
	@Transactional	
	public PagingInfo getPagingInfo(PagingInfo pagingInfo) {
		
		int totalRow = replyDao.getPagingInfo(pagingInfo);
		int totalPageCount=totalRow / pagingInfo.getPageSize();		
		
		if(totalRow%pagingInfo.getPageSize() != 0){
			totalPageCount += 1; 
		}
		
		pagingInfo.setTotalPageCount(totalPageCount);
		return pagingInfo;
	}



	private void makeInsertReply(Reply reply){
		String no = String.valueOf(reply.getNo());
		int zeroLength = 5-no.length();//자릿수
		StringBuffer path = new StringBuffer("0");

		for(int i=0;i<zeroLength;i++){
			path.append("0");
		}
		path.append(no);
		reply.setPath(path.toString());

		if(reply.getParent() == 0){//부모코드가 없는경우, 답글이 아닌 일반글

			reply.setFamily(reply.getNo());

		}else{//부모코드가 있는경우, 해당 부모코드의 familyCode와 depth를 사용한다

			Reply parentRepley = replyDao.getParent(reply.getParent());
			reply.setFamily(parentRepley.getFamily());
			reply.setDepth(parentRepley.getDepth()+1);

		}
	}
	
	@Override
	public boolean uploadImage(Reply reply, String path, MultipartFile mpf){
		MultipartFile image = mpf;
		try{
			if(image != null){
				String originName = image.getOriginalFilename();
				Tika tika = new Tika();
				String imgExt = tika.detect(image.getInputStream()).split("/")[1];
				
				if(imgExt.equalsIgnoreCase("JPG") || imgExt.equalsIgnoreCase("PNG") || imgExt.equalsIgnoreCase("GIF")){//jpg, png, gif만 허용
					String fileName = String.valueOf(reply.getNo());
					File dir = new File(path);
					if(!dir.exists()){
						dir.mkdirs();
					}
					String imageName=fileName+"."+imgExt;
					File file = new File(path + File.separatorChar + imageName);
					image.transferTo(file);
					reply.setImageName(imageName);
				}
			}
			return true;
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}

	}

	@Override
	@Transactional
	public boolean add(Reply reply){
		int no = replyDao.getNo()+1;
		reply.setNo(no);
		reply.setWriteDate(Utils.getNowTime());
		reply.setModifyDate(Utils.getNowTime());
		
		makeInsertReply(reply);

		replyDao.add(reply);
		return true;
	}

	@Override
	@Transactional
	public boolean update(Reply reply) {
		Reply update = replyDao.getReply(reply.getNo());
		update.setModifyDate(Utils.getNowTime());
		update.setContent(reply.getContent());
		update.setImageName(reply.getImageName());
		
		return true;
	}


	@Override
	@Transactional			
	public void delete(Reply reply) {
		replyDao.delete(reply);
	}

}
