package com.momo.reply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.ReplyMapper;
import com.momo.vo.Criteria;
import com.momo.vo.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyTest {
	
	@Autowired
	ReplyMapper mapper;
	
	@Test
	public void test() {
		assertNotNull(mapper);
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNo(1);
		List<ReplyVO> list = mapper.getList(83, cri);
		log.info("list : " + list);
	}
	
	@Test
	public void insert() {
		ReplyVO reply = new ReplyVO();
		
		reply.setBno(83);
		reply.setReply("댓글달기");
		reply.setReplyer("작성자");
		
		int res = mapper.insert(reply);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void delete() {
		int res = mapper.delete(17);
		assertEquals(res, 1);
	}
	
	@Test
	public void update() {
		ReplyVO reply = new ReplyVO();
		
		reply.setRno(15);
		reply.setReply("수정 확인");
		
		int res = mapper.update(reply);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void totalCnt() {
		int res = mapper.totalCnt(83);
		System.out.println(res);
	}
	
}
