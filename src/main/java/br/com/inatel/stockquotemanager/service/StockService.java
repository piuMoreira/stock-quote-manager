package br.com.inatel.stockquotemanager.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.inatel.stockquotemanager.model.dto.StockDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockService {

	@Value("${stock-manager-url}")
	private String stockApiUrl;
	private RestTemplate restTemplate = new RestTemplate();
	
	@Cacheable(value = "getAllStocks")
	public List<StockDto> getAllStocks() {
		log.info("Geting all stocks and populating 'getAllStocks' cache.");
		
		String url = stockApiUrl + "/stock";	
		StockDto[] stocks = restTemplate.getForObject(url, StockDto[].class);		
		return Arrays.asList(stocks);
	}
	
	
	@Cacheable(value = "getStock")
	public StockDto getStock(String stockId) {
		log.debug("Geting stock {} and populating 'getStock' cache", stockId);
		
		String url = stockApiUrl + "/stock/" + stockId;
		return restTemplate.getForObject(url, StockDto.class);
	}
	
	public void register() {
		log.info("Registring stock-quote-manager inthe api stock-manager");
		
		String url = stockApiUrl +"/notification";
		JSONObject json = new JSONObject();
		
		json.put("host", "localhost");
		json.put("port", "8081");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
		restTemplate.postForObject(url, entity, String.class);
	}
	
}
