package com.cos.photogramstart.domain.user;
//JAP-Java Persistence API(자바를 자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor //전체 생성자
@NoArgsConstructor //빈 생성자
@Data//Getter,Setter
@Entity//디비에 테이블 생성
public class User {
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //id 번호 자동증가 전략이 데이터베이스를 따라간다.
	private int id; 
	
	@Column(length=100, unique = true) //OAuth2 로그인을 위해 컬럼 늘리기
	private String username;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String name; // 이름
	private String website; // 자기 홈페이지
	private String bio; // 자기소개
	@Column(nullable=false)
	private String email;
	private String phone;
	private String gender;
	
	
	private String profileImageUrl;//작성자 사진
	private String role; // 권한
	
	//나는 연관관계의 주인이 아니다. 그러므로 테이블에컬럼을 만들지 마.
	//User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
	//Lazy=User를 Select할 때 해당 User id로 등록된 Image들을 가져오지마-대신 getImages()함수가 호출될 때 가져와
	//Eager=User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와
	//양방향매핑
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	//무한참조 방지
	@JsonIgnoreProperties({"user"})
	private List<Image>images;
	private LocalDateTime createDate;
	
	@PrePersist//DB에 Insert 되기 직전 실행
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role +", createDate="
				+ createDate + "]";
	}
	
	
	
}
