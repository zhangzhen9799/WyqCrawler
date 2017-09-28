package com.fh.netpf.crawler.service;

import com.fh.netpf.crawler.entity.Account;
import com.fh.netpf.crawler.mapper.AccountMapper;
import com.fh.netpf.crawler.utils.MyBatisUtils;
import lombok.val;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper = MyBatisUtils.getSession().getMapper(AccountMapper.class);

    @Override
    public List<Account> getLoginAccount() {

        val list = accountMapper.getLoginAccount();

        return list;

    }

    @Override
    public List<Account> getLoginAccountByStatus(int status) {

        val list = accountMapper.getLoginAccountByStatus(status);

        return list;

    }

    @Override
    public List<Account> getNotLoginAccount() {

        val list = accountMapper.getNotLoginAccount();

        return list;

    }

    @Override
    public List<Account> getNotLoginAccountByStatus(int status) {

        val list = accountMapper.getNotLoginAccountByStatus(status);

        return list;

    }

    @Override
    public int updateAccount(Account account) {

        int code = accountMapper.updateAccount(account);

        return code;

    }

    @Override
    public int clearLoginStatus() {

        int code = accountMapper.clearLoginStatus();

        return code;

    }

}
