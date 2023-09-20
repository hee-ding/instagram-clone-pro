package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //해당 파일(SecurityConfig)로 시큐리티를 활성화
@Configuration //Ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final OAuth2DetailsService oAuth2DetailsService;
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//'super삭제' - 시큐리티를 활성화가 된 상태에서 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨
// 내가 원하는 페이지로 이동하도록 설정
		http.csrf().disable();//CSRF토큰 비활성화
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated() //("")안에있는 단어로 시작하는 단어는.인증이 필요
			.anyRequest().permitAll() //그게 아닌 요청은.모두허용하겠다.
			.and() //그리고
			.formLogin() //접근권한이 없는 페이지를 요청했을 때(=403)
			//로그인은 시큐리티한테 위임(컨트롤러 만들지 않음)
			.loginPage("/auth/signin") //로그인페이지로 //GET
			.loginProcessingUrl("/auth/signin")//POST ->스프링 시큐리티가 로그인 프로세스 진행
			.defaultSuccessUrl("/")//정상적으로 로그인 되면 이쪽으로 이동
			.and()
			.oauth2Login() //form 로그인도하는데,oauth2로그인도 할거야
			.userInfoEndpoint() //oauth2 로그인을 하면 최종응답을 회원정보를 바로 받을 수 있다.
			.userService(oAuth2DetailsService);  
	}
}
