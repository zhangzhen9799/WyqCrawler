package com.fh.netpf.crawler.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.netpf.crawler.conf.SystemConfigure;
import com.show.api.ShowapiRequest;
import com.show.api.util.Base64;
import com.show.api.util.WebUtils;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.*;

@Log4j2
public class LoginUtils {

    /**
     * 获取账号的cookie
     *
     * @param username 用户名
     * @param password 密码
     * @return cookie字符串
     *
     */
    public static String login(String username, String password) {

        password = toMD5(password);
        String ran = getRan();
        String imgVcode = getImgVcode(ran);

        String loginUrl = "http://wyq.sina.com/indexLogin.action";
        String charset = "gbk";

        val param = new HashMap<String, String>();
        param.put("username", username);
        param.put("password", password);
        param.put("imgVcode", imgVcode);
        param.put("_ran", ran);

        List<Cookie> cookies = getCookies(loginUrl, param, charset);
        String cookieString = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("www")) {
                cookieString = cookie.getValue();
            }
        }
        if (!cookieString.equals("")) {
            cookieString = "www=" + cookieString;
        }
        return cookieString;
    }

    private static String getImgVcode(String ran) {
        try {
            ShowapiRequest req = new ShowapiRequest(SystemConfigure.getInstance().getProperty("API"), SystemConfigure.getInstance().getProperty("AppCode"));
            URL url = new URL("http://wyq.sina.com/view/common/validation.jsp?c=lg_" + ran);
            byte[] img = WebUtils.readByteFromStream(url.openStream());
            String base64string = Base64.encode(img);
            log.info(base64string);
            byte[] b = req.addTextPara("typeId", "36")
                    .addTextPara("convert_to_jpg", "1")
                    .addTextPara("img_base64", base64string)
                    .postAsByte();
            Map map = req.getRes_headMap();
            for (Object k : map.keySet()) {
                log.info(k + "          " + map.get(k));
            }
            String res = new String(b, "utf-8");
            log.info(res);
            JSONObject json = JSON.parseObject(res);
            JSONObject result = JSON.parseObject(json.get("showapi_res_body").toString());
            String imgVcode = result.getString("Result");
            log.info("识别结果:" + imgVcode);
            return imgVcode;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return "";
    }

    private static String getRan() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    private static List<Cookie> getCookies(String url, Map<String, String> param, String charset) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        List<Cookie> cookies = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            val list = new ArrayList<NameValuePair>();
            param.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            int code = httpClient.execute(httpPost).getStatusLine().getStatusCode();
            log.info("请求地址:" + url);
            log.info("请求参数:" + list.toString());
            log.info("StatusCode:{}", code);
            cookies = cookieStore.getCookies();
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("{}", e);
                }
            }
        }
        return cookies;
    }

    private static String toMD5(String plainText) {
        StringBuffer buf;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (byte aB : b) {
                i = aB;
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            log.error("{}", e);
        }
        return "";
    }

}
