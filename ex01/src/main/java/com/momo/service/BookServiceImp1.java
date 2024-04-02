package com.momo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.BookMapper;
import com.momo.vo.BookVo;
import com.momo.vo.Criteria;
import com.momo.vo.PageDto;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImp1 implements BookService {
	
	@Autowired
	BookMapper bookMapper;
	
	@Override
	public List<BookVo> getList(Criteria cri, Model model) {
		
		/*
		 * 1. 리스트 조회
		 * 2. 총 건수 조회
		 * 3. 페이지 블럭을 생성하는 pageDto 생성
		 * */
		
		List<BookVo> list = bookMapper.getList(cri);
		int total = bookMapper.getTotalCnt(cri);
		PageDto pageDto = new PageDto(cri, total);
		
		
		model.addAttribute("list", list);
		model.addAttribute("total", total);
		model.addAttribute("pageDto", pageDto);
		log.info("pageDto :"+ pageDto);
		return null;
	}

	@Override
	public int getTotalCnt(Criteria cri) {
		
		return bookMapper.getTotalCnt(cri);
	}

	@Override
	public BookVo getOne(int no, Model model) {
		BookVo book = bookMapper.getOne(no);
		model.addAttribute("book",book);
		
		return null;
	}
	
	
}
