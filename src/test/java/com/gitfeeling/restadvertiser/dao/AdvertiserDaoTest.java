package com.gitfeeling.restadvertiser.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import com.gitfeeling.restadvertiser.common.DBFactory;
import com.gitfeeling.restadvertiser.model.Advertiser;

public class AdvertiserDaoTest {
	
	private static AdvertiserDao dao;
	
	private static Map<String, Advertiser> startingMap;
	
	@BeforeClass
	public static void setup() throws IOException {
		  EmbeddedDatabase db = DBFactory.getDB();
	      dao = new AdvertiserDao();
	      dao.setJdbcTemplate(new JdbcTemplate(db));
	      
	      startingMap = new HashMap<>();
	      BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/data.csv"));
	      String line;
	      while((line = reader.readLine()) != null) {
	    	  	String[] properties = line.split(",");
	    	  	Advertiser advertiser = new Advertiser(
	    	  			properties[0], properties[1], Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
	    	  	startingMap.put(advertiser.getName(), advertiser);
	      }
	      reader.close();
	}
	
	
	@Test
	public void testFindAllSuccess() {
		assertTrue(dao.findAll().containsAll(startingMap.values()));		
	}
	
	@Test
	public void testFindByNameSuccess() { 
		String name = "DavidOgilvy";
		Advertiser advertiser = dao.findByName(name);
		assertEquals(startingMap.get(name), advertiser);
	}
	
	@Test
	public void testFindByNameNotFoundReturnsNull() {
		String name = "NoGoodAdvertiser";
		Advertiser advertiser = dao.findByName(name);
		assertNull(advertiser);
	}
	
	@Test
	public void testFindByNameNullInputReturnsNull() {
		assertNull(dao.findByName(null));
	}
	
	@Test
	public void testInsertAndUpdate() 
			throws DuplicateEntityException, DataIntegrityException {
		Advertiser advertiser = new Advertiser();
		advertiser.setName("DuggyDoLittle");
		assertEquals(1, dao.insert(advertiser));
		
		Advertiser persistedAdvertiser = dao.findByName(advertiser.getName());
		assertEquals(advertiser, persistedAdvertiser);
		
		persistedAdvertiser.setContactName("Mr Do Little");
		persistedAdvertiser.setCreditLimit(2000);
		persistedAdvertiser.setBalance(500);
		dao.update(persistedAdvertiser);
		
		Advertiser updatedAdvertiser = dao.findByName(persistedAdvertiser.getName());
		assertEquals(persistedAdvertiser, updatedAdvertiser);
		
		dao.deleteByName(updatedAdvertiser.getName()); 
	}	
	
	@Test
	public void testInsertAndDelete() 
			throws DuplicateEntityException, DataIntegrityException {
		Advertiser advertiser = new Advertiser("DuggyDoLittle", "Mr Do Little", 2000, 500);
		dao.insert(advertiser);
		assertEquals(advertiser, dao.findByName(advertiser.getName()));
		
		dao.deleteByName(advertiser.getName());
		assertNull(dao.findByName(advertiser.getName()));
	}
	
	@Test(expected = DuplicateEntityException.class)
	public void testDuplicateInsertThrowsDuplicateEntityException() 
			throws DuplicateEntityException, DataIntegrityException {
		Advertiser inAdvertiser = new Advertiser("PNG", "DuggyDolittle", 2000, 500);
		assertEquals(1, dao.insert(inAdvertiser));
		dao.insert(inAdvertiser);
	}
	
	@Test(expected = DataIntegrityException.class)
	public void testInsertMissingPrimaryKeyThrowsDataIntegrityException() 
			throws DuplicateEntityException, DataIntegrityException {
		Advertiser advertiser = new Advertiser();
		dao.insert(advertiser);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullInputThrowsIllegalArgumentException() 
			throws DuplicateEntityException, DataIntegrityException {
		dao.insert(null);
	}
	
	@Test
	public void testUpdateNotFoundReturns0() {
		Advertiser advertiser = new Advertiser("NoGoodAdvertiser", "Mr. Knows Little", 0, 0);
		assertTrue(dao.update(advertiser) == 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNullInputThrowsIllegalArgumentException() {
		dao.update(null);
	}
	
	@Test
	public void testDeleteNotFoundReturns0() {
		Advertiser advertiser = new Advertiser("NoGoodAdvertiser", "Mr. Knows Little", 0, 0);
		assertTrue(dao.deleteByName(advertiser.getName()) == 0);
	}
	
	@Test
	public void testDeleteNullInputReturns0() {
		assertTrue(dao.deleteByName(null) == 0);
	}
	
}

