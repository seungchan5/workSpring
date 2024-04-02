package com.momo.vo;

import java.util.List;

import lombok.Data;

@Data
public class MemberVo {

	private String id;
	private String pw;
	private String name;
	private String adminyn;
	private String status;
	private String grade;
	
	// 사용자 권한
	private List<String> role;
	
	public MemberVo(String id, String pw, String name, String adminyn, String status, String grade) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.adminyn = adminyn;
		this.status = status;
		this.grade = grade;
	}

	public MemberVo() {
		
	}
}
