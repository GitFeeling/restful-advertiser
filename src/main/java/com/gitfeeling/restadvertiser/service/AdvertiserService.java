package com.gitfeeling.restadvertiser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitfeeling.restadvertiser.dao.AdvertiserDao;
import com.gitfeeling.restadvertiser.dao.DuplicateEntityException;
import com.gitfeeling.restadvertiser.model.Advertiser;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException.Status;

@Service
public class AdvertiserService {
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
	public List<Advertiser> getAllAdvertisers() {
		List<Advertiser> advertisers = advertiserDao.findAll();
		return advertisers;
	}
	
	public Advertiser getAdvertiserByName(String name) throws AdvertiserServiceException {
		Advertiser advertiser = advertiserDao.findByName(name);
		if (advertiser == null) {
			throw new AdvertiserServiceException(Status.ADVERTISER_NOT_FOUND);
		}
		return advertiser;
	}
	
	public void addAdvertiser(Advertiser advertiser) throws AdvertiserServiceException {
		try {
			advertiserDao.insert(advertiser);			
		}
		catch (DuplicateEntityException ex) {
			throw new AdvertiserServiceException(Status.ADVERTISER_ALREADY_EXISTS);
		}
	}
	
	public void updateAdvertiser(Advertiser advertiser) throws AdvertiserServiceException {
		int numberOfRowsUpdated = advertiserDao.update(advertiser);
		if( numberOfRowsUpdated == 0) {
			throw new AdvertiserServiceException(Status.ADVERTISER_NOT_FOUND);
		}
	}
	
	public void deleteAdvertiser(String name) throws AdvertiserServiceException {
		int numberOfRowsDeleted = advertiserDao.deleteByName(name);
		if( numberOfRowsDeleted == 0) {
			throw new AdvertiserServiceException(Status.ADVERTISER_NOT_FOUND);
		}
	}

}
