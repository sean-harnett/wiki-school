package com.wikischool.wikischool;

import com.wikischool.wikischool.main.ConnectionObjects.JdbcConnection;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class WikiSchoolApplicationTests {
	@Autowired
	private JdbcConnection jdbcTestObject;

	/**
	 * Test if the properties are read into the connection object correctly
	 */
	@Test
	public void TestConnectionProperties(){
		jdbcTestObject.setPropertiesFromFile();
		assertThat(jdbcTestObject.getPassword()).isEqualTo("-password-"); //Test that password is stored correctly
		assertThat(jdbcTestObject.getUserName()).isEqualTo("postgres");
		assertThat(jdbcTestObject.getConnectionUrl()).isEqualTo("jdbc:postgresql://localhost:5432/test");
	}

	/**
	 *  Test if the connection object works, and a connection can be established
	 */
	@Test
	public void TestIfConnectionEstablished(){
		jdbcTestObject.setPropertiesFromFile();
		jdbcTestObject.establishConnection();
		assertThat(jdbcTestObject.checkConnection()).isEqualTo(true);
	}

}
