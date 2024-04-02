package com.momo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.momo.service.BoardService;
import com.momo.vo.BoardVO;
import com.momo.vo.Criteria;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	
	@GetMapping("/reply/test")
	public String Test() {
		return "/reply/test";
		
	}
	
	@GetMapping("message")
	public void message(Model model) {
		
	}
	
	@GetMapping("msg")
	public void msg() {
		
	}
	
	@GetMapping("list_bs")
	public void list_bs(Model model) {
		
	}
	
	@Autowired
	BoardService boardService;
	
	/**
	 * 파라메터의 자동수집
	 * 	기본 생성자를 이용해서 객체를 생성
	 * 	-> setter 메서드를 이용해서 세팅
	 * @param model
	 * @param cri
	 */
	
	@GetMapping("list")
	public void getList(Model model, Criteria cri) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		boardService.getListXml(cri, model);
		log.info("========================");
		log.info("cri : " + cri);		

		stopWatch.stop();
		log.info("수행시간 : " + stopWatch.getTotalTimeMillis()+"(ms)초");
		
	}
	
	@GetMapping("view")
	public void getOne(Model model, BoardVO paramVo) {
		log.info("=================bno" + paramVo);
		model.addAttribute("board", boardService.getOne(paramVo.getBno()));
		
	}
	
	/**
	 * requestMapping에 /borard/가 설정되어 있으므로
	 * /board/write
	 * 
	 * @param model
	 */
	@GetMapping("write")
	public void write(Model model) {
		
	}
	
	/**
	 * RedirectAttributes
	 * 
	 * 리다이렉트 URL의 화면까지 데이터를 전달
	 * 
	 * Model과 같이 매개변수로 받아 사용	
	 * addFlashAttribute : 세션에 저장 후 페이지 전환 (주소창에 메세지를 띄우고 싶지 않을때 사용)
	 */
	@PostMapping("write")
	public String writeAction(BoardVO board, List<MultipartFile> files , RedirectAttributes rttr, Model model){
		log.info(board);
		
		int res;
		
		try {
			// 시퀀스 조회 후 시퀀스 번호를 bno에 저장
			// 게시물 등록 및 파일 첨부
			res = boardService.insertSelectKey(board, files);
			
			String msg = "";
			
			if(res>0) {
				msg = board.getBno() + "번 등록되었습니다";
				// url?msg=등록(쿼리스트링으로 전달 -> param.msg)
				//rttr.addAttribute("msg",msg);
				
				// 세션영역에 저장 -> msg
				// 새로고침시 유지되지 않음
				rttr.addFlashAttribute("msg", msg);
				return "redirect:/board/list";
			} else {
				msg = "등록중 예외가 발생했습니다";
				model.addAttribute("msg",msg);
				return "/board/message";
			}
		
		} catch (Exception e) {
			log.info(e.getMessage());
			if(e.getMessage().indexOf("첨부파일")>-1) {
				model.addAttribute("msg",e.getMessage());
			} else {
				model.addAttribute("msg", "등록중 예외 발생");
			}
			
			return "/board/message";
		}
		
		
	}
	
	@GetMapping("edit")
	public String edit(BoardVO paramVo, Model model) {
		BoardVO board = boardService.getOne(paramVo.getBno());
		model.addAttribute("board",board);
		return "/board/write";
	}
	
	@PostMapping("editAction")
	public String editAction(BoardVO board, List<MultipartFile> files, Model model,RedirectAttributes rttr, Criteria cri) {
		// 수정
		int res;
		
		try {
			res = boardService.update(board, files);
			if(res>0) {			
				// redirect시 request영역이 공유되지 않으므로
				// RedirectAttributes를 이용
				// model.addAttribute("msg","수정 되었습니다");
				rttr.addFlashAttribute("msg","수정되었습니다");
				rttr.addAttribute("pageNo", cri.getPageNo());
				rttr.addAttribute("searchField", cri.getSearchField());
				rttr.addAttribute("searchWord", cri.getSearchWord());
				
				// 상세 페이지로 이동
				return "redirect:/board/view?bno=" + board.getBno() + "&pageNo=" + cri.getPageNo() + "&searchField=" +cri.getSearchField() + "&searchWorld=" +cri.getSearchWord();
			} else {
				model.addAttribute("mgs", "수정 중 오류 발생");
				return "/board/message";
			}
		} catch (Exception e) {
			if(e.getMessage().indexOf("첨부파일")>-1) {
				model.addAttribute("msg",e.getMessage());
			} else {
				model.addAttribute("msg", "등록중 예외 발생");
			}
			
			return "/board/message";
		}
	}
	
	@GetMapping("delete")
	public String delete(BoardVO board, Model model, RedirectAttributes rttr) {
		int res = boardService.delete(board.getBno());
		if(res > 0) {
			rttr.addFlashAttribute("msg","삭제되었습니다");
			return "redirect:/board/list";			
		} else {
			model.addAttribute("msg", "삭제중 오류 발생");
			return "/board/message";
		}
	}
}
