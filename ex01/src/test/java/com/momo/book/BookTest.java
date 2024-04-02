package com.momo.book;

import static org.junit.Assume.assumeNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.BookMapper;
import com.momo.vo.BookVo;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookTest {

	@Autowired
	BookMapper bookMapper;
	
	@Test
	public void getList() {
		//assumeNotNull(bookMapper);
		List<BookVo> list = bookMapper.getList(new Criteria());
		log.info(list);	
	}
	
	@Test
	public void getTotalCnt() {
		int res = bookMapper.getTotalCnt(new Criteria());
		System.out.println("총 건수 : " + res);
	}
	
	@Test
	public void getOne() {
		BookVo book = bookMapper.getOne(1193);
		System.out.println(book);
	}
}
