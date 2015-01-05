package zum.potal.dwlee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

//web.xml
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"zum.potal.dwlee"})
public class WebConfig extends WebMvcConfigurerAdapter{
	
	/**
	 * 
     * CSS / JavaScript / Image 등의 정적 리소스를 처리해주는 핸들러를 등록
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }
 
    /**
     * 인터셉터 (요청을 가로챔)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
        
        HandlerInterceptorAdapter interceptor = new Interceptor();
        registry.addInterceptor(interceptor);
    }
    
 
    /**
     * locale resolver
     *
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
 
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
        return cookieLocaleResolver;
    }
    
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }
    
//    @Bean
//    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(){
//    	ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
//    	Map<String, MediaType> map = new HashMap<String, MediaType>();
//    	map.put("json", new MediaType("application","json"));
//    	map.put("xml",  new MediaType("application","xml"));
//    	
//    	
//    	ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager(new PathExtensionContentNegotiationStrategy(map));
//    	
//    	contentNegotiatingViewResolver.setContentNegotiationManager(contentNegotiationManager);
//    	
//    	List list = new ArrayList();
//    	list.add(jsonView());
//    	contentNegotiatingViewResolver.setDefaultViews(list);
//    	return contentNegotiatingViewResolver;
//    }
    
    /**
     * JSP를 뷰로 사용하는 뷰 리졸버 등록
     */
    @Bean
    public ViewResolver viewResolver() {
 
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(2);
        return viewResolver;
    }
    
    @Bean 
    public ViewResolver BeanNameViewResolver(){
    	BeanNameViewResolver viewResolver = new BeanNameViewResolver();
    	viewResolver.setOrder(1);
    	return viewResolver;
    }
    
    @Bean
    public MappingJackson2JsonView jsonView(){
    	MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
    	jsonView.setObjectMapper(new ObjectMapper());
    	return jsonView;
    }
}
