package com.fh.netpf.crawler.downloader;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResultItems {

	private Map<String, Object> fields = new LinkedHashMap<String, Object>();

    private Request request;

    private Boolean skip = false;
	
	public Object get(String key) {
        Object o = fields.get(key);
        if (o == null) {
            return null;
        }
        return fields.get(key);
    }

    public <T> ResultItems put(String key, T value) {
        fields.put(key, value);
        return this;
    }

}
