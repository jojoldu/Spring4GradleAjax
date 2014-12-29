package zum.potal.dwlee.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
 
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
      
  @Override
  protected Class<?>[] getRootConfigClasses() {
	   return new Class<?>[]{ DataSourceConfig.class };//ModuleConfig 웹쪽 설정이 아닌 공통으로 쓰일수 있는 부분의 설정
  }
 
  @Override
  protected Class<?>[] getServletConfigClasses() {
      //return new Class<?>[] { WebConfig.class, SecurityConfig.class };
	  return new Class<?>[] { WebConfig.class};
  }
 
  //servlet-mapping
  @Override
  protected String[] getServletMappings() {
      return new String[] { "/","*.json"};
  }
 
  @Override
  protected Filter[] getServletFilters() {
 
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      
//      DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
//      return new Filter[] { characterEncodingFilter,delegatingFilterProxy};
      return new Filter[] { characterEncodingFilter};
  }
  
  
}
