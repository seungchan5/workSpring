package com.momo.fileupload;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.book.BookTest;
import com.momo.mapper.FileuploadMapper;
import com.momo.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class FileuploadTest {

	@Autowired
	FileuploadMapper fileuploadMapper;
	
	@Test
	public void insertTest() {
		FileuploadVO vo = new FileuploadVO();
		
		vo.setUploadpath("uploadpath");
		vo.setFilename("filename");
		vo.setFiletype("I");
		vo.setBno(83);
		UUID uuid = UUID.randomUUID();
		vo.setUuid(uuid.toString());
		
		
		int res = fileuploadMapper.insert(vo);
		
		assertEquals(1, res);
		
	}
	
	@Test
	public void getList() {
		List<FileuploadVO> list = fileuploadMapper.getList(83);
		log.info(list);
	}
	
	@Test
	public void delete() {
		int res = fileuploadMapper.delete(83, "d620c93a-31e5-47da-b362-89a5baaa34cd");
		assertEquals(1, res);
	}
	
	@Test
	public void getOne() {
		log.info("getOne()");
		FileuploadVO vo = fileuploadMapper.getOne(83, "29034b3a-6a18-4ff0-bbde-e329c64d9c33");
		
		assertEquals(1, vo);
	}
}
