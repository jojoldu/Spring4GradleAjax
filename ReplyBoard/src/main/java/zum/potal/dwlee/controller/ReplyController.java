package zum.potal.dwlee.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;






import zum.potal.dwlee.service.ReplyService;
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

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public @ResponseBody boolean add(ReplyVO insertVO, HttpSession session, MultipartHttpServletRequest request) throws Exception{
		UserVO loginVO = (UserVO)session.getAttribute("loginVO");
		insertVO.setWriter(loginVO.getId());	
		try{
			Iterator<String> itr =  request.getFileNames();
			if(itr.hasNext()) {
				MultipartFile mpf = request.getFile(itr.next());
				insertVO.setImage(mpf);
			}
			replyService.add(insertVO);
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