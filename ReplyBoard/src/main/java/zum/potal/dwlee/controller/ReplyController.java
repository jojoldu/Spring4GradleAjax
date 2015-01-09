package zum.potal.dwlee.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.utils.CommonConstants;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.ResponseObject;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	private final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService replyService;

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
		return "reply/list";
	}

	@RequestMapping(value="/get-list.json", method=RequestMethod.POST)
	@ResponseBody
	public Map getList(@ModelAttribute PagingInfo pagingInfo, HttpSession session){
		Map map = new HashMap();
		User login = (User)session.getAttribute(CommonConstants.LOGIN_SESSION);
		
		if(login == null){
			map.put("result", false);
			return map;
		}
		
		map.put("list", replyService.getList(pagingInfo));
		map.put("loginId", login.getId());
		map.put("loginEmail", login.getEmail());
		map.put("result", true);

		return map;
	}

	@RequestMapping(value="/get-paginginfo.json", method=RequestMethod.POST)
	@ResponseBody 
	public PagingInfo getPagingInfo(@ModelAttribute PagingInfo pagingInfo){
		return replyService.getPagingInfo(pagingInfo);
	}	

	//작성자 설정
	private void setWriter(Reply reply, HttpSession session){
		User login = (User)session.getAttribute(CommonConstants.LOGIN_SESSION);
		reply.setWriter(login.getId());	
	}
 
	//request 요청을 MultipartFile로 전환
	private MultipartFile getMultipartFile(MultipartHttpServletRequest request){

		Iterator<String> itr =  request.getFileNames();
		MultipartFile mpf=null;
		
		if(itr.hasNext()) {
			mpf = request.getFile(itr.next());
		}
		
		return mpf;
	}

	private String getPath(MultipartHttpServletRequest request){
		return request.getSession().getServletContext().getRealPath(File.separator+"resources"+File.separator+"images");
	}

	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	@ResponseBody
	public ResponseObject add(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		setWriter(reply, session);
		
		boolean resultUpload = replyService.uploadImage(reply, getPath(request), getMultipartFile(request));
		boolean resultAdd = replyService.add(reply);
		
		if(resultUpload && resultAdd){
			return new ResponseObject(true);
		}
		
		return new ResponseObject(false);
	}

	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	@ResponseBody
	public ResponseObject update(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		setWriter(reply, session);
		
		boolean resultUpload = replyService.uploadImage(reply, getPath(request), getMultipartFile(request));
		boolean resultUpdate = replyService.update(reply);
		
		if(resultUpload && resultUpdate){
			return new ResponseObject(true);
		}
		
		return new ResponseObject(false);
	}	

	@RequestMapping(value="/delete.json", method=RequestMethod.POST)
	public void delete(@ModelAttribute Reply reply) {
		replyService.delete(reply);
	}
}