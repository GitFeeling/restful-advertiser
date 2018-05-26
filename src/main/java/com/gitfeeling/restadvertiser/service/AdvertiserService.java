package com.gitfeeling.restadvertiser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gitfeeling.restadvertiser.dao.AdvertiserDao;
import com.gitfeeling.restadvertiser.exception.AdvertiserServiceException;
import com.gitfeeling.restadvertiser.model.Advertiser;

@Service
public class AdvertiserService {
	
	@Autowired
	private AdvertiserDao advertiserDao; 
	
	public List<Advertiser> getAllAdvertisers() {
		return advertiserDao.findAll();
	}
	
	public Advertiser getAdvertiserByName(String name) throws AdvertiserServiceException {
		Advertiser advertiser = advertiserDao.findByName(name);
		if (advertiser == null) {
			throw new AdvertiserServiceException(404, "Advertiser not found");
		}
		return advertiser;
	}
	
	public void addAdvertiser(Advertiser advertiser) throws AdvertiserServiceException {
		if (advertiserDao.insert(advertiser) != 1) {
			throw new AdvertiserServiceException(500, "Advertiser not inserted");
		}
	}
	
	public void updateAdvertiser(Advertiser advertiser) throws AdvertiserServiceException {
		if(advertiserDao.update(advertiser) != 1) {
			throw new AdvertiserServiceException(404, "Advertiser not found");
		}
	}
	
	public void deleteAdvertiser(String name) throws AdvertiserServiceException {
		if(advertiserDao.deleteByName(name) != 1) {
			throw new AdvertiserServiceException(404, "Advertiser not found");
		}
	}

}
