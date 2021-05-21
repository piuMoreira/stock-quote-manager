package br.com.inatel.stockquotemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.stockquotemanager.controller.form.StockQuoteForm;
import br.com.inatel.stockquotemanager.model.Quote;
import br.com.inatel.stockquotemanager.model.dto.StockDto;
import br.com.inatel.stockquotemanager.model.dto.StockQuotesDto;
import br.com.inatel.stockquotemanager.repository.QuoteRepository;
import br.com.inatel.stockquotemanager.service.StockService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/quote")
public class QuoteController {

	private QuoteRepository quoteRepository;
	
	private StockService stockService;
	
	@Autowired
	public QuoteController(QuoteRepository quoteRepository, StockService stockService) {
		this.quoteRepository = quoteRepository;
		this.stockService = stockService;
	}
	
	@GetMapping
	public ResponseEntity<?> listAllQuotes() {
		List<StockDto> stocks = stockService.getAllStocks();
		
		if(!stocks.isEmpty()) {	
			log.info("Stocks were found.");
			return ResponseEntity.ok(StockQuotesDto.convertToList(stocks, quoteRepository));
		}
		
		log.warn("There's no stocks on the database.");
		return ResponseEntity.status(404).body("There's no stocks on the database.");
	}
	
	@GetMapping("/{stockId}")
	public ResponseEntity<?> listQuotesOfAStock(@PathVariable String stockId) {
		StockDto stockDto = stockService.getStock(stockId);
		
		if(stockDto == null) {
			log.debug("Unable to find a stock {}", stockId);
			return ResponseEntity.status(404).body("Unable to find stock "+stockId);
		}
		
		List<Quote> quotes = quoteRepository.findByStockId(stockId);
		
		if(!quotes.isEmpty()) {		
			log.debug("Stock {} have quotes.", stockId);
			return ResponseEntity.ok(new StockQuotesDto(stockId, quotes));
		}
		
		log.warn("There's no quotes in the stock {}.",stockId);
		return ResponseEntity.status(404).body("There's no quotes in the stock "+stockId);
		
	}
	
	@PostMapping
	public ResponseEntity<?> addStockQuote(@RequestBody StockQuoteForm form) {
		StockDto stockDto = stockService.getStock(form.getId());
		
		if(stockDto == null) {
			log.debug("Unable to find a stock {}", form.getId());
			return ResponseEntity.status(404).body("Unable to find stock"+form.getId());
		}
		
		List<Quote> quotes = form.toListQuote();
		quoteRepository.saveAll(quotes);
		
		log.debug("{} quotes were added to stock {}", quotes.size(), form.getId());
		return ResponseEntity.created(null).body(new StockQuotesDto(form.getId(), quotes));
	}
	
}
