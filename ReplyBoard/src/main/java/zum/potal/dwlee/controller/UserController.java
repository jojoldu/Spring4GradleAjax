package zum.potal.dwlee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userServcie;
		
	@RequestMapping(value="/checkDuplicateId.json", method=RequestMethod.POST)
	public @ResponseBody Map checkDuplicateId(@ModelAttribute User user){
		Map map = new HashMap();
		
		try{
			map.put("result", userServcie.checkDuplicateId(user));
			
			return map;
		}catch(Exception e){
			logger.info(e.getMessage());
			map.put("result", false);
			
			return map;		
		}
	}
	
	@RequestMapping(value="/login.json", method=RequestMethod.POST)
	public @ResponseBody Map login(@ModelAttribute User login, HttpSession session) {
		User user;
		Map map = new HashMap();
		try{
			user=userServcie.login(login);
			if(user!=null){
				session.setAttribute("loginVO", user);
				map.put("result", true);
			}
			
			return map;	
		}catch(Exception e){
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;	
		}
	}
	
	@RequestMapping(value="/logout.json", method=RequestMethod.POST)
	public @ResponseBody boolean logout(HttpSession session) {
		try{
			session.removeAttribute("loginVO");
			
			return true;	
		}catch(Exception e){
			logger.debug(e.getMessage());
			
			return false;
		}
	}
	
	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	public @ResponseBody Map addUser(@ModelAttribute User user) {
		Map map = new HashMap();
		
		try{
			userServcie.add(user);
			map.put("result", true);
			
			return map;	

		}catch(Exception e){
			
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;	
		}
	}
	
	@RequestMapping(value="/checkPassword.json", method=RequestMethod.POST)
	public @ResponseBody Map checkPassword(@ModelAttribute User user){
		Map map = new HashMap();
		
		try{
			map.put("result", userServcie.checkPassword(user));
			
			return map;	
		}catch(Exception e){
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;	
		}
	}
	
	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public @ResponseBody Map update(@ModelAttribute User user){
		
		Map map = new HashMap();
		try{
			userServcie.update(user);
			map.put("result", true);
			
			return map;	

		}catch(Exception e){
			
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;	
			
		}
	}

	@RequestMapping(value="/delete.json", method=RequestMethod.POST)
	public Map delete(@ModelAttribute User user, HttpSession session){
		
		Map map = new HashMap();
		try{
			userServcie.delete(user);
			session.removeAttribute("loginVO");
			map.put("result", true);
			
			return map;	
 
		}catch(Exception e){
			
			logger.debug(e.getMessage());
			map.put("result", false);
			
			return map;	
			
		}
	}

}
