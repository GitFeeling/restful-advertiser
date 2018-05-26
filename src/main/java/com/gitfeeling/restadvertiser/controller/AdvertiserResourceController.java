package com.gitfeeling.restadvertiser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gitfeeling.restadvertiser.exception.AdvertiserServiceException;
import com.gitfeeling.restadvertiser.model.Advertiser;
import com.gitfeeling.restadvertiser.service.AdvertiserService;

@Controller
@Description("A controller for handling requests for the Advertiser resource")
@RequestMapping(value = "/api/advertisers")
public class AdvertiserResourceController {
	
	private AdvertiserService advertiserService;
	
	@Autowired
	public AdvertiserResourceController(AdvertiserService advertiserService) {
		this.advertiserService = advertiserService;
	}
	
	@GetMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<Advertiser> getAllAdvertisers() {
		return advertiserService.getAllAdvertisers();
	}
	
	@GetMapping("/{name}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Advertiser getAdvertiserByName(@PathVariable("name") String name) throws AdvertiserServiceException {
		return advertiserService.getAdvertiserByName(name);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addAdvertiser(@RequestBody Advertiser advertiser) throws AdvertiserServiceException {
		advertiserService.addAdvertiser(advertiser);
	}
	
	@PutMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateAdvertiser(@RequestBody Advertiser advertiser) throws AdvertiserServiceException {
		advertiserService.updateAdvertiser(advertiser);
	}
	
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAdvertiser(@PathVariable("name") String name) throws AdvertiserServiceException {
		advertiserService.deleteAdvertiser(name);
	}
}
