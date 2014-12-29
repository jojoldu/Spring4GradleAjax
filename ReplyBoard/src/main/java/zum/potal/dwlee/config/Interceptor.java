package zum.potal.dwlee.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler){
		String reqUrl = request.getRequestURI().toString();
		try{
			if("/ReplyBoard/".equals(reqUrl)){
				return true;
			}else{
				if(reqUrl!=null && reqUrl.matches(".*.json")){ // json 데이터 요청의 경우 통과 
					return true; 
				}else{//페이지 이동의 경우
					if(request.getSession().getAttribute("loginVO") == null){//세션이 없다면
						response.sendRedirect("/ReplyBoard/");
						return false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	
}
