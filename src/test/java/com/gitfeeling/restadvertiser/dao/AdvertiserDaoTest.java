package com.gitfeeling.restadvertiser.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import com.gitfeeling.restadvertiser.model.Advertiser;

public class AdvertiserDaoTest {
	
	private static AdvertiserDao dao;
	
	@BeforeClass
	public static void setup() {
		EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
	            .addScript("schema.sql")
	            .addScript("data.sql")
	            .build();
	      dao = new AdvertiserDao();
	      dao.setJdbcTemplate(new JdbcTemplate(db));
	}
	
	@Test
	public void testFindWorksSuccessfully() { 
		List<Advertiser> advertisers = dao.findAll();
		System.out.println(advertisers);
	}
	
	@Test
	public void testInsertWorksSuccessfully() throws DuplicateEntityException {
		
		Advertiser putAdvertiser = new Advertiser("PNG", "DuggyDolittle", 2300);
		assertEquals(1, dao.insert(putAdvertiser));
		
		Advertiser getAdvertiser = dao.findByName("PNG");
		assertEquals(putAdvertiser.getContactName(), getAdvertiser.getContactName());
		assertEquals(putAdvertiser.getName(), getAdvertiser.getName());
		assertEquals(putAdvertiser.getCreditLimit(), getAdvertiser.getCreditLimit());		
	}

}
