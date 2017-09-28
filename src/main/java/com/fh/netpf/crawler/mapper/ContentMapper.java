package com.fh.netpf.crawler.mapper;

import com.fh.netpf.crawler.entity.Content;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface ContentMapper {

    @Insert("INSERT INTO content(area, attribute, author, keyword, link, similar, source, text, time) VALUES(#{area}, #{attribute}, #{author}, #{keyword}, #{link}, #{similar}, #{source}, #{text}, #{time})")
    @Options(useGeneratedKeys=true, keyProperty="id")
    int insert(Content content);

}
