package com.cos.photogramstart.web;

import java.util.List;

import org.hibernate.query.criteria.internal.predicate.IsEmptyPredicate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;
//사용자로부터 데이터를 받고 서비스로 호출하기만 하면 됨
@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;

	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	//만약 API구현한다면 -이유 -(브라우저에서 요청하는게 아니라, 안드로이드,iOS에서 요청하는경우)지금은페이지 리턴이라API사용 안함
	@GetMapping({"/image/popular"})
	public String popular(Model model) {
		//api는 데이터를 리턴하는 서버인데 이거는 아님
		List<Image>images=imageService.인기사진();
		
		model.addAttribute("images",images);
		
		return "image/popular";
	}
	@GetMapping({"/image/upload"})
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
		}
		//서비스 호출
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId();
	}
}
