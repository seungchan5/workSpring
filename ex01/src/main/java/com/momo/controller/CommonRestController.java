package com.momo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.momo.vo.PageDto;
import com.momo.vo.ReplyVO;

public class CommonRestController {
	
	private final String REST_WRITE= "등록";
	private final String REST_EDIT= "수정";
	private final String REST_DELETE= "삭제";
	private final String REST_SELECT= "조회";
	protected final String REST_SUCCESS = "success";
	protected final String REST_FAIL = "fail";
	
	/**
	 * 입력, 수정, 삭제의 경우 int값을 반환
	 * 결과를 받아서 Map을 생성 후 반환
	 */
	public Map<String, Object> responseMap(int res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(res>0) {
			map.put("result", REST_SUCCESS);
			map.put("msg", msg+"되었습니다");
		} else {
			map.put("result", REST_FAIL);
			map.put("msg", msg+"중 예외발생");
		}
		
		return map;
	}
	
	public Map<String, Object> responseWriteMap(int res){
		return responseMap(res, REST_WRITE);
	}
	public Map<String, Object> responseEditMap(int res){
		return responseMap(res, REST_EDIT);
	}
	public Map<String, Object> responseDeleteMap(int res){
		return responseMap(res, REST_DELETE);
	}
	public Map<String, Object> responseListMap(List<?> list,PageDto pageDto){
		
		int res = list != null? 1:0;
		Map<String, Object> map = responseMap(1, REST_SELECT);
		map.put("list", list);
		map.put("pageDto", pageDto);
		
		return map;
	}
	
	public Map<String, Object> responseMap(String result, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
			map.put("result", result);
			map.put("msg", msg);
		
		return map;
	}
}
