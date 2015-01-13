package zum.potal.dwlee.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource datasource;
    
    @Bean(name = "requestCache")
    public RequestCache getRequestCache() {
        return new HttpSessionRequestCache();
    }
    
    @Autowired 
    private RequestCache requestCache;
    
    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
					.antMatchers("/bootstrap/**", "/css/**", "/js/**", "/plugins/**", "/resources/**");		//제한하지 않을 resource 지정
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/user/**", "/reply/**").permitAll();
		
		http
			.formLogin()
				.successHandler(ajaxAuthenticationSuccessHandler)
				.loginPage("/")
				.loginProcessingUrl("/user/login.json");
		
		http
           .sessionManagement()
           			.maximumSessions(1)
           			.expiredUrl("/")
           			.maxSessionsPreventsLogin(true)
           			.and()
           .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
           .invalidSessionUrl("/");

	}
	
//	//권한 할당
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
//    	userDetailsService.setDataSource( datasource );
//    	userDetailsService.setUsersByUsernameQuery("select id, email from user where id=? and password=?");
//    	
//    	auth.userDetailsService( userDetailsService );
//    	auth.jdbcAuthentication().dataSource( datasource );
//       
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//    	auth.userDetailsService( userDetailsService ).passwordEncoder( encoder );
//
//
//        if ( !userDetailsService.userExists( "user" ) ) {
//            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//            authorities.add( new SimpleGrantedAuthority( "USER" ) );
//            User userDetails = new User( "user", encoder.encode( "password" ), authorities );
//
//            userDetailsService.createUser( userDetails );
//        }
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}
