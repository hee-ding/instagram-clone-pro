package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em;//모든 Repository는 EntityManager을 구현해서 만들어져있는 구현체
	
	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
	    // 쿼리 준비
	    StringBuffer sb = new StringBuffer();
	    sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
	    sb.append("IF((SELECT 1 FROM subscribe WHERE fromUserId=? AND toUserId = u.id), 1, 0) subscribeState, ");
	    sb.append("IF((?=u.id), 1, 0) equalUserState ");
	    sb.append("FROM user u INNER JOIN subscribe s ");
	    sb.append("ON u.id = s.toUserId ");
	    sb.append("WHERE s.fromUserId=? "); // 세미콜론 첨부하면 안됨

	    // 1. 물음표 principalId
	    // 2. 물음표 principalId
	    // 3. 물음표 pageUserId

	    // 쿼리 완성
	    Query query = em.createNativeQuery(sb.toString())
	            .setParameter(1, principalId)
	            .setParameter(2, principalId)
	            .setParameter(3, pageUserId);

	    // 쿼리 실행 (DTO에 DB 결과를 매핑하기 위해)
	    JpaResultMapper result = new JpaResultMapper();
	    List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
	    return subscribeDtos;
	}

	@Transactional
	public void 구독하기(int fromUserId, int toUserId){
		try {
			subscribeRepository.msubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독한 회원입니다.");
		}
		
	}
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId){
		subscribeRepository.mUnsubscribe(fromUserId, toUserId);
		//m은 내가 만들었다는 의미
	}
}
