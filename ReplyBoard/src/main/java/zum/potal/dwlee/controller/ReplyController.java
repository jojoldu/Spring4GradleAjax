package zum.potal.dwlee.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	private final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Autowired
	private ReplyService replyService;

	@RequestMapping(value="/goTolist", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
		return "reply/list";
	}
	
	@RequestMapping(value="/list.json", method=RequestMethod.POST)
	public @ResponseBody Map getList(@ModelAttribute PagingInfo pagingInfo, HttpSession session){
		Map map = new HashMap();
		List list=null;
		User login = (User)session.getAttribute("loginVO");
		
		try{
			list= replyService.getList(pagingInfo);
			
			map.put("list", list);
			map.put("loginId", login.getId());
			map.put("loginEmail",login.getEmail());
			map.put("result", true);
			
			return map;
			
		}catch(Exception e){
			logger.debug(e.getMessage());
			map.put("result", false);
			return map;
		}
	}
	
	@RequestMapping(value="/getPagingInfo.json", method=RequestMethod.POST)
	public @ResponseBody Map getPagingInfo(@ModelAttribute PagingInfo pagingInfo){
		Map map = new HashMap();

		try{
			map.put("pagingInfo", replyService.getPagingInfo(pagingInfo));
			map.put("result", true);
			
			return map;
		
		}catch(Exception e){
			logger.debug(e.getMessage());
			map.put("result", false);
			return map;
		}
	}	
	
	private MultipartFile getMultipartFile(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		User login = (User)session.getAttribute("loginVO");
		reply.setWriter(login.getId());	
		
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
	public @ResponseBody Map add(Reply reply, HttpSession session, MultipartHttpServletRequest request){

		String path="";
		Map map = new HashMap();
		
		try{
			
			path = getPath(request);
			replyService.uploadImage(reply, path, getMultipartFile(reply,session,request));
			map.put("result", replyService.add(reply));
			
			return map;
			
		}catch(Exception e){
			logger.debug(e.getMessage());
			
			map.put("result", false);
			
			return map;
		}
	}
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public @ResponseBody Map update(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		String path="";
		Map map = new HashMap();
		
		try{
			
			path = getPath(request);
			replyService.uploadImage(reply, path, getMultipartFile(reply,session,request));
			map.put("result", replyService.update(reply));
			
			return map;
			
		}catch(Exception e){
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;
		}
	}	
	
	@RequestMapping(value="/delete.json", method=RequestMethod.POST)
	public @ResponseBody Map delete(@ModelAttribute Reply reply) {
		
		Map map = new HashMap();
		
		try{
			replyService.delete(reply);
			map.put("result", true);
			
			return map;

		}catch(Exception e){
			
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;
		}
	}
}