package br.com.inatel.stockquotemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.stockquotemanager.service.StockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/stockcache")
public class CacheController {

	private StockService stockService;
	
	@Autowired
	public CacheController(StockService stockService) {
		this.stockService = stockService;
		
		log.info("Calling register method.");	
		this.stockService.register();
	}
	
	@DeleteMapping
	@CacheEvict(value = "getAllStocks", allEntries = true)
	public void cleanCache() {
		log.info("Invalidating 'getAllStocks' cache.");
	}
	
}
