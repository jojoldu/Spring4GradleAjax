package zum.potal.dwlee.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import zum.potal.dwlee.utils.CommonConstants;
import zum.potal.dwlee.utils.SHA256;
import zum.potal.dwlee.vo.User;

public class Interceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(Interceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String reqUrl = request.getRequestURI().toString();
		
		try {
			if ("/".equals(reqUrl) || (reqUrl != null && reqUrl.matches(".*\\.json"))) { // json 데이터 요청의 경우 통과
				
//				String password =(String) request.getParameter("password");
//				
//				if("".equals(password)){
//					return true;
//				}
//				
//				request.setAttribute("password", SHA256.encode(password));
				
				return true;
			
			} else {
			
				if (request.getSession().getAttribute(CommonConstants.LOGIN_SESSION) == null) {// 세션이 없다면
					
					response.sendRedirect("/");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("인터셉터 오류 메세지: ", e);
		}
		return true;
	}

}
