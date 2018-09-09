package com.gitfeeling.restadvertiser.dao;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import com.gitfeeling.restadvertiser.common.DBFactory;

public class CreditDaoTest {

	private static CreditDao dao;
	
	@BeforeClass
	public static void setup() {
		EmbeddedDatabase db = DBFactory.getDB();
	      dao = new CreditDao();
	      dao.setJdbcTemplate(new JdbcTemplate(db));		
	}
	
	@Test
	public void testGetAvlCredit() throws DataNotFoundException {
		int amount = (dao.getAvlCredit("DavidOgilvy"));
		assertTrue(amount == 1000);
	}
	
	@Test(expected = DataNotFoundException.class)
	public void testGetAvlCreditAdvNotFoundThrowsDataNotFoundException() throws DataNotFoundException {
		dao.getAvlCredit("DonnieDarko");
	}
	
	@Test
	public void testUpdateBalance() throws DataNotFoundException {
		int numOfRows = dao.updateBalance("DonnyDeutsch", 1000);
		assertTrue(numOfRows == 1);
		int amount = dao.getAvlCredit("DonnyDeutsch");
		assertTrue(amount == 1000);
	}
	
	@Test
	public void testUpdateBalanceAdvNotFoundReturns0() {
		int numOfRows = dao.updateBalance("DonnyDarko", 1000);
		assertTrue(numOfRows == 0);
	}

}
