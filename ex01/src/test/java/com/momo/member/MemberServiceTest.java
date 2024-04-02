package com.momo.member;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.MemberService;
import com.momo.vo.MemberVo;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberServiceTest {

	@Autowired
	MemberService memberService;
	
	@Test
	public void login() {
		MemberVo member = new MemberVo();
		
		member.setId("admin");
		member.setPw("1234");
		
		member = memberService.login(member);
		
		log.info(member);
	}
	
	@Test
	public void testInsert() {
		MemberVo member = new MemberVo();
		member.setId("admin");
		member.setPw("1234");
		member.setName("관리자");
		
		int res = memberService.insert(member);
		
		assertEquals(1, res);
		
	}
	
	@Test
	public void testIdCheck() {
		MemberVo member = new MemberVo();
		member.setId("test2");
		
		int res = memberService.idCheck(member);
		
		assertEquals(1, res);
		
	}
}
