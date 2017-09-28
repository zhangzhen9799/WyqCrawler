package com.fh.netpf.crawler.entity;

import lombok.Data;

@Data
public class Keyword {

	private Integer id;
	
	private String keyword;
	
	private Long lastprocesstime;
	
	private String memo;

	private String status;
	
}
