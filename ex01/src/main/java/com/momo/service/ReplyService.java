package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

@Service
public interface ReplyService {
	
	public List<ReplyVO> getList(int bno, Criteria cri);
	
	public int insert(ReplyVO reply);
	
	public int delete(int rno);
	
	public int update(ReplyVO reply);
	
	public int totalCnt(int bno);
	
	
}
