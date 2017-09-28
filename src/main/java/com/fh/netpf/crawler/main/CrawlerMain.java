package com.fh.netpf.crawler.main;

import com.fh.netpf.crawler.conf.SystemConfigure;
import com.fh.netpf.crawler.service.AccountService;
import com.fh.netpf.crawler.service.AccountServiceImpl;
import com.fh.netpf.crawler.threads.ProcessKeywordThread;
import com.fh.netpf.crawler.utils.LoginUtils;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.io.*;

@Log4j2
public class CrawlerMain {

    public static void main(String[] args) {

        //打印Logo
        show();

        //加载配置文件
        init();

        //初始化登录
        checkAccount();

        //开始抓取任务
        processKeyword();

    }

    private static void processKeyword() {

        try {
            Thread processKeywordThread = new Thread(new ProcessKeywordThread());
            processKeywordThread.start();
        } catch (Exception e) {
            log.error("{}", e);
        }

    }

    private static void checkAccount() {

        AccountService accountService = new AccountServiceImpl();

        //把所有账号的登录状态置为0
        accountService.clearLoginStatus();

        //获取账号
        val accounts = accountService.getNotLoginAccount();

        //进行登录
        accounts.forEach(account -> {
            account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
            if (account.getCookie().equals("")) {
                account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                if (account.getCookie().equals("")) {
                    account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                    if (account.getCookie().equals("")) {
                        account.setLoginstatus(-1);
                        account.setStatus(-1);
                        accountService.updateAccount(account);
                        try {
                            Thread.sleep(1000 * 10);
                        } catch (InterruptedException e) {
                            log.error("{}", e);
                        }
                        return;
                    }
                }
            }
            account.setCreatetime(System.currentTimeMillis());
            account.setLastlogintime(System.currentTimeMillis());
            account.setLoginstatus(1);
            accountService.updateAccount(account);
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        });

    }

    private static void init() {

        SystemConfigure.getInstance();

    }

    private static void show() {

        File file = new File("Logo");
        BufferedReader reader = null;
        String temp;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                System.out.println(temp);
            }
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        }

    }

}
