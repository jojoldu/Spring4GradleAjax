package zum.potal.dwlee.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
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
//        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//        localeChangeInterceptor.setParamName("lang");
//        registry.addInterceptor(localeChangeInterceptor);
        
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
    	return jsonView;
    }
 
    @Override
    public void addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
 
        // 특별히 controller 를 타지 않아도 되는 뷰만 있는 경우 등록
        // ex) 디자인만 입힌 것들.
        registry.addViewController("/simpleView").setViewName("/simpleView");
 
 
        // 404 오류가 발생했을때 보여줄 뷰를 등록
        // registry.addViewController("/page-not-found").setViewName("errors/404");
    }
 
    /**
     * message source 들을 등록함
     *
     * @return
     */
    @Bean
    public MessageSource messageSource() {
 
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
        // if true, the key of the message will be displayed if the key is not
        // found, instead of throwing a NoSuchMessageException
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        // # -1 : never reload, 0 always reload
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
}
