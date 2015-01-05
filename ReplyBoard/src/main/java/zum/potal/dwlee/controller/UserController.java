package zum.potal.dwlee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		
	@RequestMapping(value="/checkDuplicateId.json", method=RequestMethod.POST)
	public @ResponseBody Map checkDuplicateId(@ModelAttribute User user){
		Map map = new HashMap();
		boolean result=false;
		
		try{
			result=userServcie.checkDuplicateId(user);
	
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		
		}finally{
			
			map.put("result",result);
		}
		return map;
	}
	
	@RequestMapping(value="/login.json", method=RequestMethod.POST)
	public @ResponseBody Map login(@ModelAttribute User login, HttpSession session) {
		User user;
		boolean result=false;
		Map map = new HashMap();
		try{
			user=userServcie.login(login);
			if(user!=null){
				session.setAttribute("loginVO", user);
				result=true;
			}

		}catch(Exception e){
			e.printStackTrace();
			result=false;
		}finally{
			map.put("result",result);
		}
		return map;
	}
	
	@RequestMapping(value="/logout.json", method=RequestMethod.POST)
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
	
	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	public @ResponseBody Map addUser(@ModelAttribute User user) {
		boolean result=false;
		Map map = new HashMap();
		try{
			userServcie.add(user);
			result=true;

		}catch(Exception e){
			
			e.printStackTrace();
			result=false;
			
		}finally{
			map.put("result", result);
		}
		
		return map;
	}
	
	@RequestMapping(value="/checkPassword.json", method=RequestMethod.POST)
	public @ResponseBody Map checkPassword(@ModelAttribute User user){
		Map map = new HashMap();
		boolean result=false;
		
		try{
			result=userServcie.checkPassword(user);
	
		}catch(Exception e){
			e.printStackTrace();
			result=false;
		
		}finally{
			
			map.put("result",result);
		}
		return map;
	}
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public @ResponseBody Map update(@ModelAttribute User user){
		
		boolean result=false;
		Map map = new HashMap();
		try{
			userServcie.update(user);
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
	public Map delete(@ModelAttribute User user, HttpSession session){
		
		boolean result=false;
		Map map = new HashMap();
		try{
			userServcie.delete(user);
			session.removeAttribute("loginVO");
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
