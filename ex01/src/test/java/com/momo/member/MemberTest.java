package com.momo.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.mapper.MemberMapper;
import com.momo.vo.MemberVo;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberTest {
	
	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void test() {
		MemberVo member = new MemberVo();
		member.setId("admin");
		member.setPw("1234");
		
		member = memberMapper.login(member);
		
		log.info(member);
		
	}
	
	@Test
	public void testInsert() {
		MemberVo member = new MemberVo();
		member.setId("test1");
		member.setPw("1234");
		member.setName("name1");
		
		int res = memberMapper.insert(member);
		
		assertEquals(1, res);
		
	}
	
	@Test
	public void testIdCheck() {
		MemberVo member = new MemberVo();
		member.setId("test1");
		
		int res = memberMapper.idCheck(member);
		
		assertEquals(0, res);
		
	}
	
	@Test
	public void testGetMemberRole() {
		List<String> list = memberMapper.getMemberRole("admin");
		System.out.println(list);
		System.out.println("관리자권한 : " + list.contains("ADMIN_ROLE"));
		}
}
