package com.momo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.vo.BookVo;
import com.momo.vo.Criteria;

@Service
public interface BookService {
	
	// 추상 메서드
	public List<BookVo> getList(Criteria cri, Model model);
	
	public int getTotalCnt(Criteria cri);
	
	public BookVo getOne(int no, Model model);

	
}
