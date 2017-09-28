package com.fh.netpf.crawler.mapper;

import com.fh.netpf.crawler.entity.Keyword;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface KeywordMapper {

    @Select("SELECT * FROM keyword ORDER BY lastprocesstime ASC")
    List<Keyword> getKeyword();

    @Update("UPDATE keyword SET lastprocesstime=#{lastprocesstime},memo=#{memo},status=#{status} WHERE id=#{id}")
    int updateKeyword(Keyword keyword);

}
