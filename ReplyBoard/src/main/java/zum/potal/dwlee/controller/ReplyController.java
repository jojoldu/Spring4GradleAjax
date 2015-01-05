package zum.potal.dwlee.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

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
		boolean result=false;
		User login = (User)session.getAttribute("loginVO");
		
		try{
			list= replyService.getList(pagingInfo);
			map.put("list", list);
			map.put("loginId", login.getId());
			map.put("loginEmail",login.getEmail());
			result=true;
		
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		
		}finally{
			map.put("result", result);
		}
		return map;
	}
	
	@RequestMapping(value="/getPagingInfo.json", method=RequestMethod.POST)
	public @ResponseBody Map getPagingInfo(@ModelAttribute PagingInfo pagingInfo){
		Map map = new HashMap();
		boolean result=false;

		try{
			map.put("pagingInfo", replyService.getPagingInfo(pagingInfo));
			result=true;
		
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		
		}finally{
			map.put("result", result);
		}
		return map;
	}	
	
	private MultipartFile getMultipartFile(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		User login = (User)session.getAttribute("loginVO");
		reply.setWriter(login.getId());	
		reply.setModifyDate(Utils.getNowTime());
		
		Iterator<String> itr =  request.getFileNames();
		MultipartFile mpf=null;
		if(itr.hasNext()) {
			 mpf = request.getFile(itr.next());
		}
		return mpf;
	}
	
	private String getPath(MultipartHttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("\\resources\\images");
		return path;
	}
	
	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	public @ResponseBody Map add(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		reply.setWriteDate(Utils.getNowTime());
		String path="";
		boolean result=false;
		Map map = new HashMap();
		
		try{
			
			path = getPath(request);
			replyService.add(reply,path,getMultipartFile(reply,session,request));
			result=true;
			
		}catch(Exception e){
			e.printStackTrace();
			result=false;
			
		}finally{
			map.put("result", result);
		}
		
		return map;
	}
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public @ResponseBody Map update(Reply reply, HttpSession session, MultipartHttpServletRequest request){
		
		String path="";
		boolean result=false;
		Map map = new HashMap();
		
		try{
			
			path = getPath(request);
			replyService.update(reply,path,getMultipartFile(reply,session,request));
			result=true;
			
		}catch(Exception e){
			e.printStackTrace();
			result=false;
			
		}finally{
			map.put("result", result);
		}
		
		return map;
	}	
	
	@RequestMapping(value="/delete.json", method=RequestMethod.POST)
	public @ResponseBody Map delete(@ModelAttribute Reply reply) {
		
		boolean result=false;
		Map map = new HashMap();
		
		try{
			replyService.delete(reply);
			result=true;

		}catch(Exception e){
			
			e.printStackTrace();
			result=false;
			
		}finally{
			map.put("result", result);
		}
		
		return map;
	}
}