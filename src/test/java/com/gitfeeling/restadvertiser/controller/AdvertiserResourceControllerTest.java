package com.gitfeeling.restadvertiser.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gitfeeling.restadvertiser.common.TestUtil;
import com.gitfeeling.restadvertiser.exception.AdvertiserServiceException;
import com.gitfeeling.restadvertiser.model.Advertiser;
import com.gitfeeling.restadvertiser.service.AdvertiserService;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class AdvertiserResourceControllerTest {
	
    private MockMvc mockMvc;
    
    @Mock
    private AdvertiserService mockAdvertiserService;
    
    @InjectMocks
    private AdvertiserResourceController advertiserController;
    
    @Before
    public void setup() {
    		mockMvc = MockMvcBuilders
    					.standaloneSetup(advertiserController)
    					.setControllerAdvice(ExceptionMappingAdvice.class)
    					.build();
    }

    @Test
    public void testGetAllAdvertisersIsSuccess() throws Exception  {
    		Advertiser first = new Advertiser("PNG", "DuggyDolittle", 2300);
    		Advertiser second = new Advertiser("OMG", "DuggyDomuch", 2400);
        when(mockAdvertiserService.getAllAdvertisers()).thenReturn(Arrays.asList(first, second));
        mockMvc.perform(get("/api/advertisers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("PNG")))
                .andExpect(jsonPath("$[0].contactName", is("DuggyDolittle")))
                .andExpect(jsonPath("$[0].creditLimit", is(2300)))
                .andExpect(jsonPath("$[1].name", is("OMG")))
                .andExpect(jsonPath("$[1].contactName", is("DuggyDomuch")))
                .andExpect(jsonPath("$[1].creditLimit", is(2400)));
    }
    
    @Test
    public void testGetAdvertiserByNameIsSuccess() throws Exception {
    		Advertiser advertiser = new Advertiser("PNG", "DuggyDolittle", 2300);
    		when(mockAdvertiserService.getAdvertiserByName("PNG")).thenReturn(advertiser);
    		mockMvc.perform(get("/api/advertisers/PNG"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.name", is("PNG")))
	            .andExpect(jsonPath("$.contactName", is("DuggyDolittle")))
	            .andExpect(jsonPath("$.creditLimit", is(2300)));
    }
    
    
    @Test
    public void testAddAdvertiserIsSuccess() throws IOException, Exception {
    		Advertiser advertiser = new Advertiser("PNG", "DuggyDolittle", 2300);
    		mockMvc.perform(post("/api/advertisers")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(TestUtil.convertObjectToJsonBytes(advertiser))
    				)
    				.andExpect(status().isCreated());			
    }
    
    
    @Test
    public void testUpdateAdvertiserIsSuccess() throws IOException, Exception {
    		Advertiser advertiser = new Advertiser("PNG", "DuggyDolittle", 2300);
    		mockMvc.perform(put("/api/advertisers")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(TestUtil.convertObjectToJsonBytes(advertiser))
    				)
    				.andExpect(status().isNoContent());
    }
    
    @Test
    public void testDeleteAdvertiserByNameIsSuccess() throws Exception {
		mockMvc.perform(delete("/api/advertisers/PNG"))
				.andExpect(status().isNoContent()); 	
    }
    
    @Test
    public void testAdvertiserServiceExceptionIsHandledGracefully() throws Exception {
	    	when(mockAdvertiserService.getAdvertiserByName(anyString()))
	    		.thenThrow(new AdvertiserServiceException(404, "Advertiser not found"));
	    	
	    	mockMvc.perform(get("/api/advertisers/PNG"))
	    			.andExpect(status().isNotFound())
	    			.andExpect(jsonPath("$.status", is(404)))
	    			.andExpect(jsonPath("$.description", is("Advertiser not found")));
    }
    
    @Test
    public void TestUnExpectedServerExceptionIsHandledGracefully() throws Exception {
     	when(mockAdvertiserService.getAdvertiserByName(anyString()))
		.thenThrow(new NullPointerException());

	     mockMvc.perform(get("/api/advertisers/PNG"))
			.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("$.status", is(ExceptionMappingAdvice.SERVER_ERROR_STATUS)))
			.andExpect(jsonPath("$.description", is(ExceptionMappingAdvice.SERVER_ERROR_MESSAGE))); 	
    }
    
}
