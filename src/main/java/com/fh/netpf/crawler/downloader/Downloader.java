package com.fh.netpf.crawler.downloader;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 页面下载
 */
@Log4j2
public class Downloader {

	private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

	private void init() {
		if (poolingHttpClientConnectionManager == null) {
			poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
			poolingHttpClientConnectionManager.setMaxTotal(100);
			poolingHttpClientConnectionManager.setDefaultMaxPerRoute(10);
		}
	}

	private CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
	}

	private HttpPost getHttpPost(String url) {
		return new HttpPost(url);
	}

	private HttpPost setHttpPostRequest(HttpPost request, Request requests) throws UnsupportedEncodingException {
		val headers = requests.getHeaders();
		headers.forEach(request::setHeader);
		val cookies = requests.getCookie();
		cookies.forEach((key, value) -> request.setHeader("Cookie", key + "=" + value));
		val list = new ArrayList<NameValuePair>();
		val param = requests.getParam();
		param.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
		if (list.size() > 0) {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, requests.getCharset());
			request.setEntity(entity);
		}
		return request;
	}

	/**
	 *
	 * 下载页面封装为Page
	 *
	 * @param requests Request请求
	 * @return Page
	 *
	 */
	public Page download(Request requests) {
		Page page = new Page();
		CloseableHttpClient httpClient = getHttpClient();
		HttpPost request = getHttpPost(requests.getUrl());
		try {
			request = setHttpPostRequest(request, requests);
			CloseableHttpResponse response = httpClient.execute(request);
			log.info("Status Code:{}", response.getStatusLine().getStatusCode());
			page.setStatusCode(response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			String result = entity != null ? EntityUtils.toString(entity, requests.getCharset()) : "";
			page.setResult(result);
			page.setRequest(requests);
			page.setHtml(new Html(Jsoup.parse(result)));
			page.setDownloadSuccess(true);
			EntityUtils.consume(entity);
			request.releaseConnection();
			response.close();
		} catch (Exception e) {
			page.setDownloadSuccess(false);
			log.info("下载失败！ {}", e.toString());
		}
		return page;
	}

}
