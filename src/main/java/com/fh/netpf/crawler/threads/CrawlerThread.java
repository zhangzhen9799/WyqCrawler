package com.fh.netpf.crawler.threads;

import com.fh.netpf.crawler.downloader.Downloader;
import com.fh.netpf.crawler.downloader.Page;
import com.fh.netpf.crawler.downloader.Request;
import com.fh.netpf.crawler.entity.Account;
import com.fh.netpf.crawler.entity.Keyword;
import com.fh.netpf.crawler.main.AppContext;
import com.fh.netpf.crawler.processor.WyqPageProcessor;
import com.fh.netpf.crawler.service.AccountService;
import com.fh.netpf.crawler.service.AccountServiceImpl;
import com.fh.netpf.crawler.utils.BuildRequest;
import com.fh.netpf.crawler.utils.LoginUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

@Log4j2
@AllArgsConstructor
public class CrawlerThread implements Runnable {

    private final AccountService accountService = new AccountServiceImpl();
    private final WyqPageProcessor wyqPageProcessor = new WyqPageProcessor();
    private final BuildRequest buildRequest = new BuildRequest();
    private Account account;
    private Keyword keyword;

    @Override
    public void run() {

        try {

            for (int i = 0; i < 50; i++) {
                //根据关键词构造请求
                Request request = buildRequest.getRequest(keyword.getKeyword(), i + "", account.getCookie());
                //下载列表页面
                Page pageTemp = new Downloader().download(request);
                //如果Cookie失效,尝试进行三次登录,并再次下载页面
                if (pageTemp.getStatusCode() == 302) {
                    //如果账号已经失效则不进行重复登录
                    if (account.getStatus() == -1 || account.getLoginstatus() == -1) {
                        return;
                    }
                    account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                    if (account.getCookie().equals("")) {
                        account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                        if (account.getCookie().equals("")) {
                            account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                            if (account.getCookie().equals("")) {
                                //设置账号信息为失效
                                account.setLoginstatus(-1);
                                account.setStatus(-1);
                                accountService.updateAccount(account);
                                break;
                            }
                        }
                    }
                    pageTemp = new Downloader().download(buildRequest.getRequest(keyword.getKeyword(), i + "", account.getCookie()));
                }

                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }

                //解析列表页面
                wyqPageProcessor.process(pageTemp);

                //如果下载失败,跳过
                if (pageTemp.getDownloadSuccess() == false && pageTemp.getResultItems().getSkip()) {
                    continue;
                }

                //获取待爬详情页面Request集合
                val requests = pageTemp.getTargetRequests();
                //遍历详情页面Request集合爬取详情
                requests.forEach((Request r) -> {
                    r.setUrl("http://wyq.sina.com/content/detail.shtml");
                    r.setCharset("GBK");
                    r.setCookie(buildRequest.buildCookie(account.getCookie()));
                    r.setHeaders(buildRequest.buildHeaders());
                    r.setMethod("POST");
                    //详情页面下载
                    Page page = new Downloader().download(r);
                    //如果Cookie失效,尝试进行三次登录,并再次下载页面
                    if (page.getStatusCode() == 302) {
                        //如果账号已经失效则不进行重复登录
                        if (account.getStatus() == -1 || account.getLoginstatus() == -1) {
                            return;
                        }
                        account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                        if (account.getCookie().equals("")) {
                            account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                            if (account.getCookie().equals("")) {
                                account.setCookie(LoginUtils.login(account.getAccount(), account.getPassword()));
                                if (account.getCookie().equals("")) {
                                    //设置账号信息为失效
                                    account.setLoginstatus(-1);
                                    account.setStatus(-1);
                                    accountService.updateAccount(account);
                                    return;
                                }
                            }
                        }
                        page = new Downloader().download(r);
                    }

                    //解析详情页
                    wyqPageProcessor.process(page);
                    log.debug("{}", page.getResultItems().toString());
                    //如未解析到内容,跳过
                    if (page.getResultItems().getSkip()) {
                        return;
                    }
                    //去重,持久化
                    AppContext.addPage(page);
                    try {
                        Thread.sleep(1000 * 5);
                    } catch (InterruptedException e) {
                        log.error("{}", e);
                    }

                });

                //如果列表项少于50,结束任务
                if (pageTemp.getStatusCode() == 200 && pageTemp.getDownloadSuccess() == true && pageTemp.getResultItems().getSkip() == true) {
                    accountService.updateAccount(account);
                    break;
                }

                //如果账号失效,结束任务
                if (account.getStatus() == -1 || account.getLoginstatus() == -1) {
                    break;
                }

            }

            accountService.updateAccount(account);

        } catch (Exception e) {
            log.error("{}", e);
        }

    }

}
