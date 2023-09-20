package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	//자바스크립트 리턴
	@ExceptionHandler(CustomValidationException.class)
//		public CMRespDto<?> validationExceprion(CustomValidationException e) {
	public String validationException(CustomValidationException e) {
		
		//CMRespDto방식
//		return new CMRespDto<Map<String,String>>(-1, e.getMessage(),e.getErrorMap());
		//CMRespDto.Script 비교
		//1. 클라이언트에게 응답할 때는 Stript 좋음 (브라우저)
		//2. 하지만 나중에 Ajax통신을 하게 되면 - CMRespDto (개발자)
		//3. Android통신도 -CMRespDto (개발자)
		
		//Script방식
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
	}
		
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
		
	}
	//데이터 리턴 (ajax응답)
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiExceprion(CustomValidationApiException e) {
		System.out.println("==========나 실행 됨==========");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiExceprion(CustomApiException e) {
		System.out.println("==========나 실행 됨==========");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
}
