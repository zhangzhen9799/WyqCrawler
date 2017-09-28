package com.fh.netpf.crawler.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * mybatis连接数据库的工具类
 */
@Log4j2
public class MyBatisUtils {

	private MyBatisUtils() {}

	private static final String RESOURCE = "mybatis-config.xml";
	private static SqlSessionFactory sqlSessionFactory = null;
	private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

	static {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(RESOURCE));
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			sqlSessionFactory = builder.build(inputStream);
		} catch (Exception e) {
			log.error("{}", e);
			throw new ExceptionInInitializerError("初始化MyBatis错误，请配置文件或数据库");
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

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static SqlSession getSession() {
		// sessionTL的get()方法根据当前线程返回其对应的线程内部变量?
		// 也就是我们需要的Session，多线程情况下共享数据库链接是不安全的?
		// ThreadLocal保证了每个线程都有自己的Session?
		SqlSession session = threadLocal.get();
		// 如果session为null
		if (session == null) {
			session = (sqlSessionFactory != null) ? sqlSessionFactory.openSession() : null;
			threadLocal.set(session); // 5
		}
		return session;
	}

	public static void closeSession() {
		SqlSession session = (SqlSession) threadLocal.get(); // 2
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}
}