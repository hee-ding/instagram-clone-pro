package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor 
@NoArgsConstructor 
@Data//Getter,Setter
@Entity
public class Image {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	private String caption; //오늘 나 너무 피곤해!
	private String postImageUrl;//사진을 전송 받아서 그 사진을 서버 특정 폴더에 저장 - DB에 그 저장된 경로를 insert = 경로를 가짐(사진은 서버에)
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user;
	
	//추후에 추가
	//이미지 좋아요 정보
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy ="image")
	private List<Likes>likes;
	
	//댓글
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Comment>comments;
	

	
	@Transient//DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	
	//항상데이터베이스 저장시 필요한 부분
	private LocalDateTime createDate;
	
	@PrePersist//DB에 Insert 되기 직전 실행
	public void createDate() {
		this.createDate=LocalDateTime.now();
	}
//오브젝트를 콘솔에 출력할때 문제가 될 수 있어서User부분을 출력되지 않게 함.
	
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl 
//				+ ", createDate=" + createDate + "]";
//	}	
	
	
	
}
