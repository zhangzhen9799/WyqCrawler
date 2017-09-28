package com.fh.netpf.crawler.downloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Page {

	private Request request;
	
	private ResultItems resultItems = new ResultItems();
	
	private String result;
	
	private Html html;
	
	private int statusCode;
	
	private Boolean downloadSuccess;
	
	private List<Request> targetRequests = new ArrayList<Request>();
	
	public Page setSkip(boolean skip) {
        resultItems.setSkip(skip);
        return this;
    }
	
	public void putField(String key, Object field) {
        resultItems.put(key, field);
    }
	
	public void addTargetRequest(Request request) {
        targetRequests.add(request);
    }
	
	public void addTargetRequest(Map<String, String> param) {
        Request request = new Request();
        request.setParam(param);
        targetRequests.add(request);
    }
	
}
