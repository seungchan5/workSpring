package com.momo.mapper;

import java.util.List;

import com.momo.vo.MemberVo;

public interface MemberMapper {

	public MemberVo login(MemberVo member);
	public int insert(MemberVo member);
	public int idCheck(MemberVo member);
	
	
	public List<String> getMemberRole(String id);
}
