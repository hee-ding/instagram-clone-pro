package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.photogramstart.domain.image.Image;

public interface CommentRepository extends JpaRepository<Comment,Integer>{


}
