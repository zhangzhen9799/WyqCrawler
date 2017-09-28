package com.fh.netpf.crawler.service;

import com.fh.netpf.crawler.entity.Keyword;
import com.fh.netpf.crawler.mapper.KeywordMapper;
import com.fh.netpf.crawler.utils.MyBatisUtils;
import lombok.val;

import java.util.List;

public class KeywordServiceImpl implements KeywordService {

    private final KeywordMapper keywordMapper = MyBatisUtils.getSession().getMapper(KeywordMapper.class);

    @Override
    public List<Keyword> getKeyword() {

        val list = keywordMapper.getKeyword();

        return list;

    }

    @Override
    public int updateKeyword(Keyword keyword) {

        int code = keywordMapper.updateKeyword(keyword);

        return code;

    }
}
