package zum.potal.dwlee.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler){
		String reqUrl = request.getRequestURI().toString();
		try{
			if(!reqUrl.matches(".*.json")){ // json 데이터를 요청하지 않는, 일반적인 페이지 이동은 생략한다. 
				return true; 
			}
//			if(!reqUrl.matches(".*/login.json") && request.getSession().getAttribute("loginVO") == null){
//				response.sendRedirect("/ReplyBoard/login");
//				return false;
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	
}
