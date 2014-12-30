package zum.potal.dwlee.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userServcie;
	
	@RequestMapping(value="/checkId.json", method=RequestMethod.POST,  headers="Accept=application/json")
	public @ResponseBody boolean checkId(@RequestBody User loginVO){
		boolean result=false;
		try{
			result=userServcie.checkId(loginVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/login.json", method=RequestMethod.POST,  headers="Accept=application/json")
	public @ResponseBody boolean login(@RequestBody User loginVO, HttpSession session) {
		User user;
		boolean result=false;
		try{
			user=userServcie.login(loginVO);
			if(user!=null){
				session.setAttribute("loginVO", user);
				result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/logout.json", method=RequestMethod.POST,  headers="Accept=application/json")
	public @ResponseBody boolean logout(HttpSession session) {
		boolean result=false;
		try{
			session.removeAttribute("loginVO");
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/list.json", method=RequestMethod.GET)
	public List<User> getList(Model model){
		List list=null;
		try{
			list= userServcie.getList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value="/add.json", method=RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody boolean addUser(@RequestBody User insertVO) {
		try{
			userServcie.add(insertVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	@RequestMapping(value="/checkPassword.json", method=RequestMethod.POST, headers="Accept=application/json")
	public boolean checkPassword(@RequestBody User userVO, Model model){
		boolean result = false;
		try{
			result = userServcie.checkPassword(userVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST, headers="Accept=application/json")
	public boolean update(@RequestBody User updateVO, Model model){
		boolean result = false;
		try{
			userServcie.update(updateVO);
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value="/delete.json", method=RequestMethod.POST, headers="Accept=application/json")
	public boolean delete(@RequestBody User deleteVO, Model model, HttpSession session){
		boolean result = false;
		try{
			userServcie.delete(deleteVO);
			session.removeAttribute("loginVO");
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

}
