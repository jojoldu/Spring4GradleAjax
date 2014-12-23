package zum.potal.dwlee.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import zum.potal.dwlee.service.ReplyService;
import zum.potal.dwlee.vo.Common;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	private final String FILE_PATH = "C:\\Users\\dwlee\\jojoldu\\ReplyBoard\\src\\main\\webapp\\resources\\images\\";

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
		User loginVO = (User)session.getAttribute("loginVO");
		if(loginVO == null){
			return "redirect:/";
		}
		return "reply/list";
	}

	@RequestMapping(value="/getList", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<Reply> getList(@RequestBody Reply listVO){
		List list=null;
		try{
			list= replyService.getList(listVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="/getPagingInfo", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody Common getPagingInfo(@RequestBody Common pagingVO){
		try{
			replyService.getPagingInfo(pagingVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pagingVO;
	}	

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody boolean add(Reply insertVO, HttpSession session, MultipartHttpServletRequest request) throws Exception{
		User loginVO = (User)session.getAttribute("loginVO");
		insertVO.setWriter(loginVO.getId());	
		try{
			//String path = request.getSession().getServletContext().getRealPath("/images/");//톰캣용
			String path = FILE_PATH;
			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf=null;
			if(itr.hasNext()) {
				 mpf = request.getFile(itr.next());
			}
			replyService.add(insertVO,path,mpf);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody boolean update(Reply updateVO, HttpSession session, MultipartHttpServletRequest request) throws Exception{
		User loginVO = (User)session.getAttribute("loginVO");
		updateVO.setWriter(loginVO.getId());	
		try{
			//String path = request.getSession().getServletContext().getRealPath("/images/");//톰캣용
			String path = FILE_PATH;
			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf=null;
			if(itr.hasNext()) {
				mpf = request.getFile(itr.next());
			}
			replyService.add(updateVO,path,mpf);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody boolean delete(@RequestBody Reply deleteVO) throws Exception{
		try{
			replyService.delete(deleteVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}