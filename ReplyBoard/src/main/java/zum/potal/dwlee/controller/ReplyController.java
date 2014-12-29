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
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.PagingInfo;
import zum.potal.dwlee.vo.Reply;
import zum.potal.dwlee.vo.User;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Autowired
	private ReplyService replyService;

//	private final String FILE_PATH = "C:\\Users\\dwlee\\jojoldu\\ReplyBoard\\src\\main\\webapp\\resources\\images\\";

	@RequestMapping(value="/goTolist", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
//		User loginVO = (User)session.getAttribute("loginVO");
//		if(loginVO == null){
//			return "redirect:/";
//		}
		return "reply/list";
	}
	
	@RequestMapping(value="/list.json", method=RequestMethod.POST, headers="Accept=application/json")
	public String getList(@RequestBody PagingInfo listVO, Model model, HttpSession session){
		List list=null;
		User loginVO = (User)session.getAttribute("loginVO");
		try{
			list= replyService.getList(listVO);
			model.addAttribute("list", list);
			model.addAttribute("loginId", loginVO.getId());
			model.addAttribute("loginEmail",loginVO.getEmail());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "jsonView";
	}
	
	@RequestMapping(value="/getPagingInfo.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody PagingInfo getPagingInfo(@RequestBody PagingInfo pagingVO){
		try{
			replyService.getPagingInfo(pagingVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pagingVO;
	}	

	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	public @ResponseBody boolean add(Reply insertVO, HttpSession session, MultipartHttpServletRequest request){
		User loginVO = (User)session.getAttribute("loginVO");
		insertVO.setWriter(loginVO.getId());	
		insertVO.setWriteDate(Utils.getNowTime());
		insertVO.setModifyDate(Utils.getNowTime());
		String path="";
		try{
			path = request.getSession().getServletContext().getRealPath("\\resources\\images");
			//path = FILE_PATH;
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
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public @ResponseBody boolean update(Reply updateVO, HttpSession session, MultipartHttpServletRequest request){
		User loginVO = (User)session.getAttribute("loginVO");
		updateVO.setWriter(loginVO.getId());	
		updateVO.setModifyDate(Utils.getNowTime());
		String path="";
		try{
			path = request.getSession().getServletContext().getRealPath("\\resources\\images");
			//path = FILE_PATH;
			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf=null;
			if(itr.hasNext()) {
				mpf = request.getFile(itr.next());
			}
			replyService.update(updateVO,path,mpf);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}	
	
	@RequestMapping(value="/delete.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody boolean delete(@RequestBody Reply deleteVO) {
		try{
			replyService.delete(deleteVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}