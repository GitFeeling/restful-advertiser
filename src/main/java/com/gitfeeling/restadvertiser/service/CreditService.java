package com.gitfeeling.restadvertiser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gitfeeling.restadvertiser.dao.CreditDao;
import com.gitfeeling.restadvertiser.dao.DataNotFoundException;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException.Status;

@Service
public class CreditService {

	@Autowired
	private CreditDao creditDao;
	
	public boolean checkBalance(String advName, int amount) throws AdvertiserServiceException {
		try {
			int avlCredit = creditDao.getAvlCredit(advName);
			return (avlCredit >= amount);
		} catch (DataNotFoundException e) {
			throw new AdvertiserServiceException(Status.ADVERTISER_NOT_FOUND);
		}
	}

	@Transactional
	public void transact(String advName, int amount) throws AdvertiserServiceException {
		if (checkBalance(advName, amount)) {
			creditDao.updateBalance(advName, amount);
		} else {
			throw new AdvertiserServiceException(Status.INSUFFICIENT_CREDIT);
		}
	}
}
