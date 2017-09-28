package com.fh.netpf.crawler.threads;

import com.fh.netpf.crawler.entity.Keyword;
import com.fh.netpf.crawler.service.AccountService;
import com.fh.netpf.crawler.service.AccountServiceImpl;
import com.fh.netpf.crawler.service.KeywordService;
import com.fh.netpf.crawler.service.KeywordServiceImpl;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class ProcessKeywordThread implements Runnable {

    private final AccountService accountService = new AccountServiceImpl();
    private final KeywordService keywordService = new KeywordServiceImpl();

    @Override
    public void run() {

        while (true) {

            //获取账号
            val accounts = accountService.getLoginAccount();
            //根据账号数量获取关键词
            int number = accounts.size();
            val keywordsTemp = keywordService.getKeyword();
            val keywords = cutKeywords(number, keywordsTemp);

            //根据账号数量初始化线程池
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(number);

            for (int i = 0; i < number; i++) {

                //开始爬取
                log.info("关键词：{}", keywords.get(i).getKeyword());
                fixedThreadPool.execute(new CrawlerThread(accounts.get(i), keywords.get(i)));

            }

            fixedThreadPool.shutdown();

            boolean isClose = true;

            //判断线程池中的任务是否执行完毕
            while (isClose) {
                if (fixedThreadPool.isTerminated()) {
                    isClose = false;
                }
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    log.error("{}", e);
                }
            }

            //任务执行完毕更新keyword表
            keywords.forEach(keyword -> {
                keyword.setLastprocesstime(System.currentTimeMillis());
                keywordService.updateKeyword(keyword);
            });

        }
    }

    private List<Keyword> cutKeywords(int size, List<Keyword> keywords) {

        val list = new ArrayList<Keyword>();

        for (int i = 0; i < size; i++) {
            list.add(keywords.get(i));
        }

        return list;

    }

}
