package zum.potal.dwlee.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.UserVO;

@RestController
@RequestMapping("/user")
public class UserController {

	@Resource(name="userService")
	private UserService userServcie;
	
//    // 접속하는 사용자에 대한 세션을 보관하기 위해 정의
//    private SessionManager clients;
	
	@RequestMapping(value="/login", method=RequestMethod.POST,  headers="Accept=application/json")
	public @ResponseBody boolean login(@RequestBody UserVO loginVO, HttpSession session) throws Exception{
		boolean result=false;
		try{
			result=userServcie.login(loginVO);
			if(result){
				session.setAttribute("loginVO", loginVO);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public List<UserVO> getList(Model model){
		List list=null;
		try{
			list= userServcie.getList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody boolean addUser(@RequestBody UserVO insertVO) throws Exception{
		try{
			userServcie.add(insertVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}
