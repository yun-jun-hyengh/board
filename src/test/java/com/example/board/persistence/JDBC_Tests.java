package com.example.board.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock.Catch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.board.persistence.JDBC_Tests;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class JDBC_Tests {
	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnection() {
		try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr")){
			log.info(connection);
		} catch(Exception e) {
			// JUnit의 메소드로서, 무조건 실패로 처리한 뒤 실항을 중지한다 
			fail(e.getMessage());
		}
	}
}