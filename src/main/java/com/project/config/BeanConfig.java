package com.project.config;

import com.project.model.*;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class BeanConfig {

	@Bean
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		ds.setUsername("scott");
		ds.setPassword("tiger");
		ds.setInitialSize(5);
		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMinEvictableIdleTimeMillis(60000 * 3);
		ds.setTimeBetweenEvictionRunsMillis(1000 * 10);
		
		return ds;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public UserDao userDao(DataSource dataSource) {
		return new UserDao(dataSource);
	}
	@Bean
	public PostDao postDao(DataSource dataSource) {
		return new PostDao(dataSource);
	}
	
	@Bean
	public ReplyDao replyDao(DataSource dataSource){
		return new ReplyDao(dataSource);
	}
	
	@Bean
	public ReplySO replySO() {
		ReplySO replySO = new ReplySO();
		
		return replySO;
	}
}
