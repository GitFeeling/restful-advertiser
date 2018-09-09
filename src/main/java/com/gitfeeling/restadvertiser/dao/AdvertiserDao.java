package com.gitfeeling.restadvertiser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gitfeeling.restadvertiser.model.Advertiser;

@Repository
public class AdvertiserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	class AdvertiserRowMapper implements RowMapper<Advertiser>{

		@Override
		public Advertiser mapRow(ResultSet rs, int rowNum) throws SQLException {
			Advertiser advertiser = new Advertiser();
			advertiser.setName(rs.getString("name"));
			advertiser.setContactName(rs.getString("contact_name"));
			advertiser.setCreditLimit(rs.getInt("credit_limit"));
			advertiser.setBalance(rs.getInt("balance"));
			return advertiser;
		}
		
	}
	
	public List<Advertiser> findAll() {
		return jdbcTemplate.query("select * from advertiser", new AdvertiserRowMapper());
	}

	public Advertiser findByName(String name) {
		try {
			return jdbcTemplate.queryForObject(
					"select * from advertiser where name=?", 
					new Object[] { name },
					new BeanPropertyRowMapper<Advertiser>(Advertiser.class));			
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int deleteByName(String name) {
		return jdbcTemplate.update(
				"delete from advertiser where name=?", 
				new Object[] { name });
	}
	
	public int update(Advertiser advertiser) {
		if (advertiser == null) {
			throw new IllegalArgumentException("Advertiser should be non null");
		}
		return jdbcTemplate.update(
				"update advertiser " + " set contact_name = ?, credit_limit = ? , balance = ?"  + " where name = ?",
				new Object[] { advertiser.getContactName(), advertiser.getCreditLimit(), advertiser.getBalance(), advertiser.getName() });
	}	

	public int insert(Advertiser advertiser) throws DuplicateEntityException, DataIntegrityException {
		if (advertiser == null) {
			throw new IllegalArgumentException("Advertiser should be non null");
		}
		try {
			return jdbcTemplate.update(
					"insert into advertiser (name, contact_name, credit_limit, balance) " + "values(?, ?, ?, ?)",
					new Object[] { advertiser.getName(), advertiser.getContactName(), advertiser.getCreditLimit(), advertiser.getBalance() });			
		} catch (DuplicateKeyException e) {
			throw new DuplicateEntityException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(e.getMessage());
		}
	}
	
}

