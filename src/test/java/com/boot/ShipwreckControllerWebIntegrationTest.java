package com.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.boot.model.Shipwreck;
import com.boot.repository.ShipwreckRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes=App.class)
@SpringBootTest(classes = App.class, webEnvironment=WebEnvironment.RANDOM_PORT) 
public class ShipwreckControllerWebIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	RestTemplate restTemplate = new RestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testListAll() throws IOException {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/shipwrecks"),
				HttpMethod.GET, entity, String.class);
		
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode responseJson = objectMapper.readTree(response.getBody());
		
		assertThat(responseJson.isMissingNode(), is(false));
		assertThat(responseJson.size(), equalTo(2));
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
