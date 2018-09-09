package com.gitfeeling.restadvertiser.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.gitfeeling.restadvertiser.dao.CreditDao;
import com.gitfeeling.restadvertiser.dao.DataNotFoundException;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException.Status;

@RunWith(MockitoJUnitRunner.class)
public class CreditServiceTest {

	@Mock
	private CreditDao dao;
	
	@InjectMocks
	private CreditService service;
	
	@Test
	public void testCheckBalanceCreditAvailable() throws DataNotFoundException, AdvertiserServiceException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenReturn(100);
		assertTrue(service.checkBalance(name, amount));	
	}
	
	@Test
	public void testCheckBalanceCreditNotAvailable() throws DataNotFoundException, AdvertiserServiceException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenReturn(50);
		assertFalse(service.checkBalance(name, amount));
	}
	
	@Test
	public void testCheckBalanceDebit() throws  DataNotFoundException, AdvertiserServiceException {
		String name = "ReynoldWoodcock";
		int amount = -100;
		when(dao.getAvlCredit(name)).thenReturn(50);
		assertTrue(service.checkBalance(name, amount));
	}	
	
	@Test
	public void testCheckBalanceAdvNotFound() throws DataNotFoundException, AdvertiserServiceException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenThrow(DataNotFoundException.class);
		try {
			service.checkBalance(name, amount);			
		} catch (AdvertiserServiceException ex) {
			assertTrue(Status.ADVERTISER_NOT_FOUND == ex.getStatus());
		}
	}
	
	@Test
	public void testTransact() throws AdvertiserServiceException, DataNotFoundException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenReturn(300);
		when(dao.updateBalance(name, amount)).thenReturn(1);
		service.transact(name, amount);
	}
	
	@Test
	public void testTransactInsufficientCredit() throws DataNotFoundException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenReturn(50);
		try {
			service.transact(name, amount);			
		}
		catch(AdvertiserServiceException ex) {
			assertTrue(Status.INSUFFICIENT_CREDIT == ex.getStatus());
		}
	}	

	@Test
	public void testTransactAdvNotFound() throws DataNotFoundException {
		String name = "ReynoldWoodcock";
		int amount = 100;
		when(dao.getAvlCredit(name)).thenThrow(DataNotFoundException.class);
		try {
			service.transact(name, amount);			
		}
		catch(AdvertiserServiceException ex) {
			assertTrue(Status.ADVERTISER_NOT_FOUND == ex.getStatus());
		}
	}
	
}
