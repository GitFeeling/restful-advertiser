package com.gitfeeling.restadvertiser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.gitfeeling.restadvertiser.model.Advertiser;
import com.gitfeeling.restadvertiser.model.ErrorResponse;
import com.gitfeeling.restadvertiser.service.AdvertiserService;
import com.gitfeeling.restadvertiser.service.AdvertiserServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@Description("A controller for handling requests for the Advertiser resource")
@RequestMapping(value = "/api/advertisers")
@Api(value = "AdvertisersControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertiserResourceController {
	   
	private AdvertiserService advertiserService;
	
	@Autowired
	public AdvertiserResourceController(AdvertiserService advertiserService) {
		this.advertiserService = advertiserService;
	}
	
	@GetMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Gets the list of advertisers in the database")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = Advertiser.class, responseContainer = "List"), 
			@ApiResponse(code = 500, message = "Unexpected server error", response = ErrorResponse.class)})
	public List<Advertiser> getAllAdvertisers() {
		return advertiserService.getAllAdvertisers();
	}
	
	@GetMapping("/{name}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Gets an advertiser specified by name")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Ok", response = Advertiser.class),
			@ApiResponse(code = 404, message = "Advertiser not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Unexpected server error", response = ErrorResponse.class)})
	public Advertiser getAdvertiserByName(@PathVariable("name") String name) throws AdvertiserServiceException {
		return advertiserService.getAdvertiserByName(name);
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adds a new advertiser to the database")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Advertiser created"),
			@ApiResponse(code = 409, message = "Advertiser already exists", response = ErrorResponse.class),
			@ApiResponse(code = 422, message = "Advertiser name is missing", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Unexpected server error", response = ErrorResponse.class)})
	public void addAdvertiser(@RequestBody Advertiser advertiser) throws AdvertiserServiceException {
		advertiserService.addAdvertiser(advertiser);
	}
	
	@PutMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Updates an existing advertiser")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Advertiser updated"),
			@ApiResponse(code = 404, message = "Advertiser not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Unexpected server error", response = ErrorResponse.class)})	
	public void updateAdvertiser(@RequestBody Advertiser advertiser) throws AdvertiserServiceException {
		advertiserService.updateAdvertiser(advertiser);
	}
	
	@DeleteMapping("/{name}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Deletes an existing advertiser")
	@ApiResponses(value = { 
			@ApiResponse(code = 204, message = "Advertiser deleted"),
			@ApiResponse(code = 404, message = "Advertiser not found", response = ErrorResponse.class),
			@ApiResponse(code = 500, message = "Unexpected server error", response = ErrorResponse.class)})	
	public void deleteAdvertiser(@PathVariable("name") String name) throws AdvertiserServiceException {
		advertiserService.deleteAdvertiser(name);
	}
}
