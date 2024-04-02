package com.momo.mapper;

import java.util.List;

import com.momo.vo.BookVo;
import com.momo.vo.Criteria;

public interface BookMapper {

	public List<BookVo> getList(Criteria cri);
	
	public int getTotalCnt(Criteria cri);
	
	public BookVo getOne(int no);
}
