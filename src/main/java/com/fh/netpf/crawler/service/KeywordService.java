package com.fh.netpf.crawler.service;

import com.fh.netpf.crawler.entity.Keyword;

import java.util.List;

public interface KeywordService {

    List<Keyword> getKeyword();

    int updateKeyword(Keyword keyword);

}
