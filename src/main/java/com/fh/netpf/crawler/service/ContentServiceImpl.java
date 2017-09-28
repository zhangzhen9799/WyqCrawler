package com.fh.netpf.crawler.service;

import com.fh.netpf.crawler.downloader.ResultItems;
import com.fh.netpf.crawler.entity.Content;
import com.fh.netpf.crawler.mapper.ContentMapper;
import com.fh.netpf.crawler.utils.MyBatisUtils;

public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper = MyBatisUtils.getSession().getMapper(ContentMapper.class);

    @Override
    public int insert(ResultItems resultItems) {

        Content content = new Content();

        content.setArea(resultItems.get("area").toString());
        content.setAttribute(resultItems.get("attribute").toString());
        content.setAuthor(resultItems.get("author").toString());
        content.setKeyword(resultItems.get("keyword").toString());
        content.setLink(resultItems.get("link").toString());
        content.setSimilar(resultItems.get("similar").toString());
        content.setSource(resultItems.get("source").toString());
        content.setTime(resultItems.get("time").toString());
        content.setText(resultItems.get("text").toString());

        int code = contentMapper.insert(content);

        return code;
    }

}
