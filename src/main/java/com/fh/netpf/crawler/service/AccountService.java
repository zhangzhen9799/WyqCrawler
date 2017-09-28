package com.fh.netpf.crawler.service;

import com.fh.netpf.crawler.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> getLoginAccount();

    List<Account> getLoginAccountByStatus(int status);

    List<Account> getNotLoginAccount();

    List<Account> getNotLoginAccountByStatus(int status);

    int updateAccount(Account account);

    int clearLoginStatus();

}
