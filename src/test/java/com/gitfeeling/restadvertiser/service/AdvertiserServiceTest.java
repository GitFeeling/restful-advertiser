package com.gitfeeling.restadvertiser.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.gitfeeling.restadvertiser.dao.AdvertiserDao;
import com.gitfeeling.restadvertiser.dao.DataIntegrityException;
import com.gitfeeling.restadvertiser.dao.DuplicateEntityException;
import com.gitfeeling.restadvertiser.model.Advertiser;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException.Status;

@RunWith(MockitoJUnitRunner.class)
public class AdvertiserServiceTest {

	@Mock
	private AdvertiserDao dao;

	@InjectMocks
	private AdvertiserService service;
	
	@Test
	public void testGetAllAdvertisers() {
		Advertiser first = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		Advertiser second = new Advertiser("NuttyProfessor", "Eddie Murphy", 2000);
		Advertiser third = new Advertiser();
		third.setContactName("BigLwebowski");
		List<Advertiser> advertisers = Arrays.asList(first, second, third);
		when(dao.findAll()).thenReturn(advertisers);
		assertTrue(service.getAllAdvertisers().size() == 3);
		assertTrue(service.getAllAdvertisers().containsAll(advertisers));
	}
	
	@Test
	public void testGetAdvertiserByName() throws AdvertiserServiceException {
		Advertiser advertiser = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		when(dao.findByName(advertiser.getName())).thenReturn(advertiser);
		assertEquals(advertiser, service.getAdvertiserByName(advertiser.getName()));
	}
	
	@Test
	public void testGetAdvertiserByNameNotFoundThrowsAdvertiserServiceException() {
		String name = "DrStrangelove";
		when(dao.findByName(name)).thenReturn(null);
		try {
			service.getAdvertiserByName(name);
		} catch (AdvertiserServiceException ex) {
			assertEquals(Status.ADVERTISER_NOT_FOUND, ex.getStatus());
		}
	}
	
	@Test
	public void testAddAdvertiser() throws DuplicateEntityException, DataIntegrityException, AdvertiserServiceException {
		Advertiser advertiser = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		when(dao.insert(advertiser)).thenReturn(1);
		service.addAdvertiser(advertiser);
	}
	
	@Test
	public void testAddAdvertiserDuplicateThrowsAdvertiserServiceException() throws DuplicateEntityException, DataIntegrityException {
		Advertiser advertiser = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		when(dao.insert(advertiser)).thenThrow(DuplicateEntityException.class);
		try {
			service.addAdvertiser(advertiser);
		} catch (AdvertiserServiceException ex) {
			assertEquals(Status.ADVERTISER_ALREADY_EXISTS, ex.getStatus());
		}
	}
	
	@Test
	public void testAddAdvertiserInvalidDataThrowsAdvertiserServiceException() throws DuplicateEntityException, DataIntegrityException {
		Advertiser advertiser = new Advertiser(null, "Peter Sellers", 1000);
		when(dao.insert(advertiser)).thenThrow(DataIntegrityException.class);
		try {
			service.addAdvertiser(advertiser);
		} catch (AdvertiserServiceException ex) {
			assertEquals(Status.ADVERTISER_MISSING_NAME, ex.getStatus());
		}
	}
	
	@Test
	public void updateAdvertiser() throws AdvertiserServiceException {
		Advertiser advertiser = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		when(dao.update(advertiser)).thenReturn(1);
		service.updateAdvertiser(advertiser);
	}
	
	@Test
	public void updateAdvertiserNotFoundThrowsAdvertiserServiceException() {
		Advertiser advertiser = new Advertiser("DrStrangelove", "Peter Sellers", 1000);
		when(dao.update(advertiser)).thenReturn(0);
		try {
			service.updateAdvertiser(advertiser);			
		} catch(AdvertiserServiceException ex) {
			assertEquals(Status.ADVERTISER_NOT_FOUND, ex.getStatus());
		}
		
	}
	
	@Test
	public void deleteAdvertiser() throws AdvertiserServiceException {
		String name = "DrStrangelove";
		when(dao.deleteByName(name)).thenReturn(1);
		service.deleteAdvertiser(name);
	}
	
	@Test
	public void deleteAdvertiserNotFoundThrowsAdvertiserServiceException() {
		String name = "DrStrangelove";
		when(dao.deleteByName(name)).thenReturn(0);	
		try {
			service.deleteAdvertiser(name);
		} catch (AdvertiserServiceException ex) {
			assertEquals(Status.ADVERTISER_NOT_FOUND, ex.getStatus());
		}
	}	
	
}
