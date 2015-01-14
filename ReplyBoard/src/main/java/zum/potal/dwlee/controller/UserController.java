package zum.potal.dwlee.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import zum.potal.dwlee.service.UserService;
import zum.potal.dwlee.utils.CommonConstants;
import zum.potal.dwlee.vo.EncodeUser;
import zum.potal.dwlee.vo.ResponseObject;
import zum.potal.dwlee.vo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	private boolean checkAuthUser(User user, HttpSession session){
		User loginUser = (User) session.getAttribute(CommonConstants.LOGIN_SESSION);
		if(loginUser == null){
			return false;
		}
		return loginUser.getId().equals(user.getId());
	}
	
	@RequestMapping(value="/check/duplicate-id", method=RequestMethod.POST)
	public ResponseObject checkDuplicateId(User user){
		
		return new ResponseObject(userService.checkDuplicateId(user.getId()));
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseObject login(@EncodeUser User user, HttpSession session) {
		User loginInfo= userService.getUser(user);
		ResponseObject result = new ResponseObject(); 

		if(loginInfo != null){
			session.setAttribute(CommonConstants.LOGIN_SESSION, loginInfo);
			result.setResult(true);
		}

		return result;
	}

	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public ResponseObject logout(HttpSession session) {
		
		session.removeAttribute(CommonConstants.LOGIN_SESSION);
		return new ResponseObject(true);	
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseObject addUser(@EncodeUser @Valid User user) {
		
		return new ResponseObject(userService.add(user));
	}

	@RequestMapping(value="/check/password", method=RequestMethod.POST)
	public ResponseObject checkPassword(@EncodeUser User user){
		
		return new ResponseObject(userService.checkPassword(user));
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ResponseObject update(@EncodeUser @Valid User user, HttpSession session){
		
		if(checkAuthUser(user,session)){
			return new ResponseObject(false);
		}
		
		return new ResponseObject(userService.update(user));
	}

	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public ResponseObject delete(HttpSession session){
		
		userService.delete((User) session.getAttribute(CommonConstants.LOGIN_SESSION));
		session.removeAttribute(CommonConstants.LOGIN_SESSION);
		
		return new ResponseObject(true);
	}

}
