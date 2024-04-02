package com.momo.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.service.LogService;
import com.momo.vo.LogVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class LogServiceTest {

	@Autowired
	LogService logService;
	
	@Test
	public void test() {
		LogVO vo = new LogVO();
		vo.setClassname("classname2");
		vo.setMethodname("methodname2");
		vo.setParams("params2");
		vo.setErrmsg("errmsg2");
		
		int res = logService.insert(vo);
		System.out.println("res : " + res);
		
	}
}
