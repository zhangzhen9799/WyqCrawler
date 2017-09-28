package com.fh.netpf.crawler.conf;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class SystemConfigure {

    private static SystemConfigure INSTANCE = null;

    private static final String CONFIG_FILE = "crawler.properties";

    private static Map<String, String> KV = new ConcurrentHashMap<String, String>(64);

    private SystemConfigure() {
        try {
            load();
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    public synchronized static SystemConfigure getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SystemConfigure();
        }
        return INSTANCE;
    }

    public String getProperty(String key) {
        String value = null;
        if (null != KV && null != key && key.trim().length() > 0) {
            value = KV.get(key.trim());
        }
        return value;
    }

    private void load(){

        InputStream inputStream = null;
        try {

            inputStream = new FileInputStream(new File(CONFIG_FILE));
            Properties properties = new Properties();
            properties.load(inputStream);

            if (properties != null && properties.size() > 0) {
                KV.clear();
                Iterator<Object> i = properties.keySet().iterator();
                while (i.hasNext()) {
                    Object key = i.next();
                    KV.put((String) key, (String) properties.get(key));
                    log.info("Load property [" + key + "] values [" + properties.get(key) + "]!");
                }
            }
        } catch (IOException e) {
            log.error("{}", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("{}", e);
                }
            }
        }
    }

}
