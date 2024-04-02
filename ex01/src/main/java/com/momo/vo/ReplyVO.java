package com.momo.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private int rno;
	private int bno;
	
	private String reply;
	private String replyer;
	private String replyDate;
	private String updateDate;
	
}
