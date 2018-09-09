package com.gitfeeling.restadvertiser.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreditDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int getAvlCredit(String advName) throws DataNotFoundException {
		try {
			return jdbcTemplate.queryForObject(
					"select (credit_limit - balance) from advertiser where name = ?",
					new Object[] { advName },
					Integer.class);
		} catch (EmptyResultDataAccessException ex) {
			throw new DataNotFoundException("Advertiser not found");
		}
	}
	
	public int updateBalance(String advName, int amount) {
		return jdbcTemplate.update(
				"update advertiser set balance = ? where name = ?",
				new Object[] {amount, advName});
	}
	
}
