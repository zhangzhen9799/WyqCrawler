package com.fh.netpf.crawler.mapper;

import com.fh.netpf.crawler.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountMapper {

    @Select("SELECT * from account WHERE loginstatus = 1")
    List<Account> getLoginAccount();

    @Select("SELECT * from account WHERE loginstatus = 1 AND status = #{status}")
    List<Account> getLoginAccountByStatus(@Param("status") int status);

    @Select("SELECT * from account WHERE loginstatus = 0")
    List<Account> getNotLoginAccount();

    @Select("SELECT * from account WHERE loginstatus = 1 AND status = #{status}")
    List<Account> getNotLoginAccountByStatus(@Param("status") int status);

    @Update("UPDATE account SET cookie=#{cookie},createtime=#{createtime},lastlogintime=#{lastlogintime},loginstatus=#{loginstatus},status=#{status} WHERE id=#{id}")
    int updateAccount(Account account);

    @Update("UPDATE account SET loginstatus = 0 WHERE loginstatus = 1")
    int clearLoginStatus();

}
