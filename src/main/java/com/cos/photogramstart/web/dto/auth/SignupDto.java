package com.cos.photogramstart.web.dto.auth;


import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

//요청하는 dto 응답 dto는 별도
//통신을 위해서 담아야하는 데이터
@Data //Getter,Setter를 만들어주는 어노테이션	
public class SignupDto {
	
	@Size(min=2, max=20)
	private String username;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String email;
	
	@NotBlank
	private String name;
	
	//데이터를 담을 때 가장간단한 방법 -> 함수만들기
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
