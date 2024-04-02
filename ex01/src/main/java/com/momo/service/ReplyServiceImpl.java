package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momo.mapper.BoardMapper;
import com.momo.mapper.ReplyMapper;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	ReplyMapper mapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public List<ReplyVO> getList(int bno, Criteria cri) {
		
		return mapper.getList(bno, cri);
	}
	
	/**
	 * Transactional
	 * 	서비스 로직에 대한 트랜잭션 처리를 지원
	 * 	오류 발생시 롤백
	 */
	@Transactional
	@Override
	public int insert(ReplyVO reply) {
		// 댓글 입력시 Board 테이블의 댓글수(replyCnt)를 1 증가 시켜줍니다.
		boardMapper.updateReplyCnt(reply.getBno(), 1);
		return mapper.insert(reply);
	}
	
	@Transactional
	@Override
	public int delete(int rno) {
		ReplyVO vo = mapper.getOne(rno);
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public int update(ReplyVO reply) {
		
		return mapper.update(reply);
	}

	@Override
	public int totalCnt(int bno) {
		
		return mapper.totalCnt(bno);
	}

}
