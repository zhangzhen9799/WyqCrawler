package com.fh.netpf.crawler.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fh.netpf.crawler.downloader.Page;

import lombok.val;
import lombok.extern.log4j.Log4j2;

/**
 * 页面解析
 */
@Log4j2
public class WyqPageProcessor implements PageProcessor {

	private static String searchResult = "http://wyq.sina.com/searchResult.action";
	private static String detail = "http://wyq.sina.com/content/detail.shtml";
	private static String listLoading = "http://wyq.sina.com/listLoading.action";

	/**
	 *
	 * 根据请求的url调用不同的解析方法
	 *
	 * @param page
	 */
	@Override
	public void process(Page page) {
		if (page.getDownloadSuccess()) {
			if (page.getRequest().getUrl().equals(searchResult)) {
				searchResultProcess(page);
				return;
			}
			if (page.getRequest().getUrl().equals(detail)) {
				detailProcess(page);
				return;
			}
			if (page.getRequest().getUrl().equals(listLoading)) {
				listLoadingProcess(page);
				return;
			}
		} else {
			page.getResultItems().setSkip(true);
		}
	}

	private void listLoadingProcess(Page page) {

		Document document = page.getHtml().getDocument();
		Elements elements = document.select("ul.list_con").select("li.rel");
		
		if (elements.toString().equals("")) {
			log.info("页面无内容！");
			page.getResultItems().setSkip(true);
			return;
		}
		
		for (Element element : elements) {
			String href = element.select("span.lc_1").select("a").get(0).attr("href");
			href = href.replaceAll("'", "");
			val param = paramProcess(href);
			param.put("searchKeyword", page.getRequest().getParam().get("secondSearchWord"));
			page.addTargetRequest(param);
		}

		//如果列表项小于50,设置跳过
		if (elements.size() < 50) {

			page.getResultItems().setSkip(true);

		}

	}

	private void detailProcess(Page page) {

		Document document = page.getHtml().getDocument();
		if (document.select("table").toString().equals("")) {
			log.info("页面无内容！");
			page.getResultItems().setSkip(true);
			return;
		}
		
		String source = document.select("table.inforTable").select("td").get(1).text();
		String area = document.select("table.inforTable").select("td").get(3).text();
		String attribute = document.select("table.inforTable").select("td").get(5).text();
		String keyword = page.getRequest().getParam().get("searchKeyword");
		String similar = document.select("table.inforTable").select("td").get(9).text();
		String time = document.select("table.inforTable").select("td").get(11).text();
		String author = document.select("table.inforTable").select("td").get(13).text();
		String link = document.select("table.inforTable").select("td").get(17).text();
		String text = document.select("div.contentBox").select("div.contenttext").text();

		// log.info("解析结果 来源:{} 所属地域:{} 属性:{} 涉及关键词:{} 相似文章:{} 发布时间:{} 作者:{}
		// 原文链接:{} 正文:{}", source, area, attribute, keywords, similar, time,
		// author, link, text);

		page.getResultItems().setRequest(page.getRequest());
		page.putField("source", source);
		page.putField("area", area);
		page.putField("attribute", attribute);
		page.putField("keyword", keyword);
		page.putField("similar", similar);
		page.putField("time", time);
		page.putField("author", author);
		page.putField("link", link);
		page.putField("text", text);
		
		log.debug("来源:{}", source);
		log.debug("所属地域:{}", area);
		log.debug("属性:{}", attribute);
		log.debug("涉及关键词:{}", keyword);
		log.debug("相似文章:{}", similar);
		log.debug("发布时间:{}", time);
		log.debug("作者:{}", author);
		log.debug("原文链接:{}", link);
		log.debug("正文:{}", text);

	}

	private void searchResultProcess(Page page) {

		Document document = page.getHtml().getDocument();
		Elements elements = document.select("table.contenttext").select("tr");
		if (elements.size() > 0) {
			elements.remove(0);
		}
		if (elements.toString().equals("")) {
			log.info("页面无内容！");
			page.getResultItems().setSkip(true);
			return;
		}
		for (Element element : elements) {
			String href = element.select("div.tit").select("a").attr("href");
			href = href.replaceAll("'", "");
			val param = paramProcess(href);
			param.put("searchKeyword", page.getRequest().getParam().get("searchKeyword"));
			page.addTargetRequest(param);
		}

		//如果列表项小于50,设置跳过
		if (elements.size() < 50) {

			page.getResultItems().setSkip(true);

		}

	}

	private Map<String, String> paramProcess(String href) {

		val param = new HashMap<String, String>();
		String con_id = null;
		String titleHs = null;
		String con_customFlag1 = null;
		String con_repeatNum = null;

		Matcher matcher = Pattern.compile("(\\d+),.?(w?b?\\d+),...?,(\\d),(\\d)").matcher(href);
		if (matcher.find()) {
			con_id = matcher.group(1);
			titleHs = matcher.group(2);
			con_customFlag1 = matcher.group(3);
			con_repeatNum = matcher.group(4);
		}

		if (con_id != null && titleHs != null && con_customFlag1 != null && con_repeatNum != null) {
			param.put("con.id", con_id);
			param.put("titleHs", titleHs);
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			param.put("start_time", df.format(date) + " 00:00:00");
			param.put("end_time", df.format(date) + " 23:59:59");
			param.put("otherAttribute", "1");
			param.put("filterOrigina", "1");
			param.put("province", "");
//			param.put("searchKeyword", "");
			param.put("secondSearchWord", "");
			param.put("searchPipeiType", "1");
			param.put("con.customFlag1", con_customFlag1);
			param.put("con.repeatNum", con_repeatNum);
			param.put("solrType", "1");
			param.put("timeDomain", "24");
			param.put("newlstSelect", "1");
			param.put("involveKeyword", "undefined");
			log.info("解析结果 {} {} {} {}", con_id, titleHs, con_customFlag1, con_repeatNum);
		}

		return param;

	}

}
