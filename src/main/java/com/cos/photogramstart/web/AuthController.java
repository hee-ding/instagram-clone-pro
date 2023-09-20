package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor //fianl 필트를 DI할때 사용
@Controller //1.Ioc등록 2.파일을 리턴하는 컨트롤러 
public class AuthController {
	
	
//	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	//@Autowired-첫번째 방법
	
	private final AuthService authService; //final을 걸어준다.하지만 오류가 난다. 자바에서는 전역변수에 파이널이 걸리면 생성자가 실행되거나, 객체가 만들어질 때 초기화를 무조건 해줘야하기 때문이다.
	//이때 @RequiredArgsConstructor 를 해주면 final이 걸려있는 모든 애들에 생성자를 만들어준다.!
	
	//AuthController의 생성자 만들기-두번째 방법
	//이유는 @Controller가 붙어있으면 스프링이 어스컨트롤러를 자기들 컨테이너를 관리하는 메모리에 로드하는데 (메모리 객체를 생성해서)  객체를 생성하기 위해서는 첫번째 조건이 생성자가 있어야 한다.
	//AuthController 이 생성자를 실행하려고 하는데 authService) 이곳에 무엇을 넣어줘어야 한다. 그래서 이미 IoC에 등록한 애들 중에 (AuthService 타입이 있으면 this.authService = authService;에 넣어준다.
	//이것이 의존성 주입이다. 하지만 귀찮기때문에 사용하지 않는 방법..
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
	
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//signup의 post메서드
	//회원가입버튼->/auth/signup -> 리턴은 /auth/signin 하지만 403으로 이동한다. CSR토큰이 활성화 되어있기 때문이다.
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { //key=value(x-www-form-urlencoded
//		if(signupDto.getUsername().length()>20) {
//			System.out.println("길이초과");
//		}else <<복잡해짐 그래서 전처리기
		
		//데이터에 들어가서 길이를 확인하고 오류를 내보냄
/*		if(bindingResult.hasErrors()) {
			Map<String,String>errorMap = new HashMap<>();
			
			for(FieldError error:bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(),error.getDefaultMessage());
				System.out.println("==================");
				System.out.println(error.getDefaultMessage());
				}
				throw new CustomValidationException("유효성 검사 실패",errorMap);
			}else {*/
				//핵심기능
				User user = signupDto.toEntity();
				authService.회원가입(user);
				//System.out.println(userEntity); 
				
				//로그 남기는 후처리
				return "auth/signin";
			}
	}

		//데이터 받기
//		log.info(signupDto.toString());
//		//User라는 오브젝트에 <- singupDto에서 받은 데이터를 넣음(4개 값)
//		User user = signupDto.toEntity();
////		log.info(user.toString());
//		User userEntity = authService.회원가입(user);
//		System.out.println(userEntity); //데이터 저장이 되지 않는다면, yml에서 update를 create로 바꾸고 나서 다시 update로 진행
//		return "auth/signin";
		//ResponseBody가 붙어있으면 데이터를 반
//	}
//}
