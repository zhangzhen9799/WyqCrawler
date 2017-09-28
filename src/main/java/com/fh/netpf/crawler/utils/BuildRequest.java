package com.fh.netpf.crawler.utils;

import com.fh.netpf.crawler.downloader.Request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 构造请求
 */
public class BuildRequest {

    //可控制参数获得不同来源数据
//    private static final String url = "http://wyq.sina.com/searchResult.action";

    //只有新浪微博
    private static final String url = "http://wyq.sina.com/listLoading.action";

    private static final String method = "POST";

    private static final String charset = "GBK";

    private Map<String, String> param;

    private Map<String, String> cookie;

    private Map<String, String> headers;

    public Request getRequest(String keyword, String page, String cookie) {

        Request request = new Request();

        request.setUrl(url);
        request.setMethod(method);
        request.setCharset(charset);
        request.setCookie(buildCookie(cookie));
        request.setParam(buildWeiBoParam(keyword, page));
        request.setHeaders(buildHeaders());

        return request;

    }

    public Map<String, String> buildCookie(String s) {

        cookie = new HashMap<>();

        s = s.substring(4, s.length());

        cookie.put("www", s);

        return cookie;

    }

    private Map<String, String> buildParam(String keyword, String page) {

        param = new HashMap<>();

        param.put("checkedIds", "");
        param.put("selectedId", "");
        param.put("searchKeyword", keyword);
        param.put("filterKeyword", "");
        param.put("searchPipeiType", "1");
        param.put("pinglunShow", "0");
        param.put("zhaiyaoShow", "1");
        param.put("duplicateShow", "0");
        param.put("paixu", "2");
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        param.put("endtime", df.format(date) + " 23:59:59");
        param.put("starttime", df.format(date) + " 00:00:00");
        param.put("clickFilterOrigina", "2");
        param.put("filterOrigina", "2");
        param.put("clickPaixu", "2");
        param.put("clickOtherAttribute", "1");
        param.put("clickDuplicateShow", "-1");
        param.put("solrType", "1");
        param.put("solrSecondType", "3");
        param.put("otherAttribute", "1");
        param.put("fontSizeType", "1");
        param.put("clickFontSizeType", "-1");
        param.put("newlstSelect", "1");
        param.put("clickNewlstSelect", "-1");
        param.put("comblineflg", "2");
        param.put("clickComblineflg", "-1");
        param.put("clickZhaiyaoShow", "-1");
        param.put("timeDomain", "24");
        param.put("clickTimeDomain", "-2");
        param.put("secondSearchWord", "");
        param.put("flag", "");
        param.put("page", page);
        param.put("hotNews", "0");
        param.put("search", "0");
        param.put("toolbarSwitch", "0");
        param.put("selectOtherTime", "0");
        param.put("ctk.categoryId", "");
        param.put("ctk.title", "");
        param.put("ctk.keyword", "");
        param.put("ctk.filterKeyword", "");
        param.put("searchType", "0");
        param.put("isRecommended", "0");

        return param;

    }

    private Map<String, String> buildWeiBoParam(String keyword, String page) {

        param = new HashMap<>();

        param.put("checkedIds", "");
        param.put("selectedId", "");
        param.put("kw.keywordId", "");
        param.put("kw.keywordName", "");
        param.put("pinglunShow", "0");
        param.put("zhaiyaoShow", "1");
        param.put("duplicateShow", "0");
        param.put("paixu", "2");
        param.put("highLightKeywords", "");
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        param.put("endtime", df.format(date) + " 23:59:59");
        param.put("starttime", df.format(date) + " 00:00:00");
        param.put("clickFilterOrigina", "-1");
        param.put("filterOrigina", "1");
        param.put("clickPaixu", "-1");
        param.put("clickOtherAttribute", "-1");
        param.put("clickDuplicateShow", "-1");
        param.put("otherAttribute", "1");
        param.put("fontSizeType", "1");
        param.put("clickFontSizeType", "-1");
        param.put("newlstSelect", "1");
        param.put("clickNewlstSelect", "-1");
        param.put("comblineflg", "2");
        param.put("clickComblineflg", "2");
        param.put("clickZhaiyaoShow", "-1");
        param.put("fwebsite.id", "");
        param.put("fwebsite.name", "");
        param.put("website", "新浪微博");//
        param.put("timeDomain", "24");
        param.put("clickTimeDomain", "24");
        param.put("flag", "");
        param.put("secondSearchWord", keyword);//
        param.put("searchPipeiType", "1");
        param.put("type", "3");
        param.put("province", "");
        param.put("estimateNumber", "9999");//
        param.put("orderRecordId", "0");
        param.put("similarConPay", "0");
        param.put("similarCon", "1");
        param.put("keywordId", "");
        param.put("con.titleHs", "");
        param.put("page", page);

        return param;
    }

    public Map<String, String> buildHeaders() {

        headers = new HashMap<>();

        Random random = new Random();
        int x = random.nextInt(100) % 2;

        if (x == 0) {
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3141.7 Safari/537.36");
        } else {
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.3408.400 QQBrowser/9.6.12028.400");
        }

        return headers;

    }

}
