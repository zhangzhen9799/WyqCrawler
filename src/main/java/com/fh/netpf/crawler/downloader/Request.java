package com.fh.netpf.crawler.downloader;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

	private String url;

    private String method;
    
    private String charset;
    
    private Map<String, String> param = new HashMap<String, String>();
    
    private Map<String, String> cookie = new HashMap<String, String>();

    private Map<String, String> headers = new HashMap<String, String>();
    
    public Request(){}
    
}
