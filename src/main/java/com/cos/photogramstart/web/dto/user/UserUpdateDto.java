package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	
	//조금 위험함. 코드 수정필요
	public User toEntity() {
		return User.builder()
				.name(name) //이름 기재 안했으면 문제 Validation 체크
				.password(password) //패흐워드를 기재안했으면 데이터베이스에 공백이 들어가버리는 것이 문제 -> Validation 체크
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
