package zum.potal.dwlee.controller;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
import zum.potal.dwlee.vo.CommonVO;
import zum.potal.dwlee.vo.ReplyVO;
import zum.potal.dwlee.vo.UserVO;

@Controller
@RequestMapping("/reply")
public class ReplyController {

	@Resource(name="replyService")
	private ReplyService replyService;


	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, HttpSession session){
		UserVO loginVO = (UserVO)session.getAttribute("loginVO");
		if(loginVO == null){
			return "redirect:/";
		}
		return "reply/list";
	}

	@RequestMapping(value="/getList", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody List<ReplyVO> getList(@RequestBody ReplyVO listVO){
		List list=null;
		try{
			list= replyService.getList(listVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="/getPagingInfo", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody CommonVO getPagingInfo(@RequestBody CommonVO pagingVO){
		try{
			replyService.getPagingInfo(pagingVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pagingVO;
	}	

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody boolean add(ReplyVO insertVO, HttpSession session, MultipartHttpServletRequest request) throws Exception{
		UserVO loginVO = (UserVO)session.getAttribute("loginVO");
		insertVO.setWriter(loginVO.getId());	
		try{
			String path = WebUtils.getRealPath(request.getSession().getServletContext(), "/images");
			Iterator<String> itr =  request.getFileNames();
			if(itr.hasNext()) {
				MultipartFile mpf = request.getFile(itr.next());
				insertVO.setImage(mpf);
			}
			replyService.add(insertVO,path);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody boolean delete(@RequestBody ReplyVO deleteVO) throws Exception{
		try{
			replyService.delete(deleteVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
}