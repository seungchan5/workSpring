package com.momo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.momo.mapper.MemberMapper;
import com.momo.vo.Member;
import com.momo.vo.MemberVo;

@Service
public interface MemberService {
	
	public MemberVo login(MemberVo member);
	public int insert(MemberVo member);
	public int idCheck(MemberVo member);
	public void naverLogin(HttpServletRequest request, Model model);

	
//	public Member login(Member paramMember, Model model) {
//	 	Member member = dao.login(paramMember);
//	 	if(member == null) {
//	 		model.addAttribute("message", "아이디/비밀번호 확인");
//	 	} else {
//	 		model.addAttribute("message", member.getName()+"님 환영");
//	 	}
//	 	return member;
//	}
}
