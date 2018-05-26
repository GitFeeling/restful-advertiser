package com.gitfeeling.restadvertiser.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gitfeeling.restadvertiser.model.Advertiser;

@Repository
public class AdvertiserDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	class AdvertiserRowMapper implements RowMapper<Advertiser>{

		@Override
		public Advertiser mapRow(ResultSet rs, int rowNum) throws SQLException {
			Advertiser advertiser = new Advertiser();
			advertiser.setName(rs.getString("name"));
			advertiser.setContactName(rs.getString("contact_name"));
			advertiser.setCreditLimit(rs.getInt("credit_limit"));
			return advertiser;
		}
		
	}
	
	public List<Advertiser> findAll() {
		return jdbcTemplate.query("select * from advertiser", new AdvertiserRowMapper());
	}

	public Advertiser findByName(String name) {
		return jdbcTemplate.queryForObject("select * from advertiser where name=?", new Object[] { name },
				new BeanPropertyRowMapper<Advertiser>(Advertiser.class));
	}

	public int deleteByName(String name) {
		return jdbcTemplate.update("delete from advertiser where name=?", new Object[] { name });
	}

	public int insert(Advertiser advertiser) {
		return jdbcTemplate.update("insert into advertiser (name, contact_name, credit_limit) " + "values(?,  ?, ?)",
				new Object[] { advertiser.getName(), advertiser.getContactName(), advertiser.getCreditLimit() });
	}

	public int update(Advertiser advertiser) {
		return jdbcTemplate.update("update advertiser " + " set contact_name = ?, credit_limit = ? " + " where name = ?",
				new Object[] { advertiser.getContactName(), advertiser.getCreditLimit(), advertiser.getName() });
	}
	
}

