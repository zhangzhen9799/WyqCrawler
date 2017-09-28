package com.fh.netpf.crawler.main;

import com.fh.netpf.crawler.downloader.Page;
import com.fh.netpf.crawler.service.ContentService;
import com.fh.netpf.crawler.service.ContentServiceImpl;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Log4j2
public class AppContext {

    private static final ContentService contentService = new ContentServiceImpl();

    public static HashSet<String> FILTER_SET = new HashSet<>(1000000);

    public static void addPage(Page page) {

        try {

            if (FILTER_SET.size() > 999999) {

                FILTER_SET.clear();

            }

            if (FILTER_SET.contains(page.getResultItems().get("link").toString())) {

                return;

            }

            if (FILTER_SET.add(page.getResultItems().get("link").toString())) {

                contentService.insert(page.getResultItems());

            }

        } catch (Exception e) {

            log.error("{}", e);

        }

    }

}
