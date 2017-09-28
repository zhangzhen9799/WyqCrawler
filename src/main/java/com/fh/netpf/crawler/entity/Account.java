package com.fh.netpf.crawler.entity;

import lombok.Data;

@Data
public class Account {

	private Integer id;
	
	private String domain;
	
	private String account;
	
	private String password;
	
	private String cookie;
	
	private Long createtime;
	
	private Long lastlogintime;
	
	private Integer loginstatus;
	
	private Integer status;

}
