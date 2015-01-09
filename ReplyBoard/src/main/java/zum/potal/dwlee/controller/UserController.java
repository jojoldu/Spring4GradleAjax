package zum.potal.dwlee.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.utils.CommonConstants;
import zum.potal.dwlee.utils.Utils;
import zum.potal.dwlee.vo.ResponseObject;
import zum.potal.dwlee.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userServcie;

	@RequestMapping(value="/check/duplicate-id.json", method=RequestMethod.POST)
	public ResponseObject checkDuplicateId(@ModelAttribute User user){
		return new ResponseObject(userServcie.checkDuplicateId(user));
	}

	@RequestMapping(value="/login.json", method=RequestMethod.POST)
	public ResponseObject login(@ModelAttribute User login, HttpSession session) {
		User user= userServcie.login(login);
		ResponseObject result = new ResponseObject(); 

		if(user != null){
			session.setAttribute(CommonConstants.LOGIN_SESSION, user);
			result.setResult(true);
		}

		return result;
	}

	@RequestMapping(value="/logout.json", method=RequestMethod.POST)
	public boolean logout(HttpSession session) {
		session.removeAttribute(CommonConstants.LOGIN_SESSION);
		return true;	
	}

	@RequestMapping(value="/add.json", method=RequestMethod.POST)
	public ResponseObject addUser(@ModelAttribute User user) {
		
		if(Utils.checkValidUser(user)){
			return new ResponseObject(false);
		}
		
		return new ResponseObject(userServcie.add(user));
	}

	@RequestMapping(value="/check/password.json", method=RequestMethod.POST)
	public ResponseObject checkPassword(@ModelAttribute User user){
		return new ResponseObject(userServcie.checkPassword(user));
	}

	@RequestMapping(value="/update.json", method=RequestMethod.POST)
	public ResponseObject update(@ModelAttribute User user){
		return new ResponseObject(userServcie.update(user));
	}

	@RequestMapping(value="/delete.json", method=RequestMethod.POST)
	public void delete(@ModelAttribute User user, HttpSession session){
		userServcie.delete(user);
		session.removeAttribute(CommonConstants.LOGIN_SESSION);
	}

}
