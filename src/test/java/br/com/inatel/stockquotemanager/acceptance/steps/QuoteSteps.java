package br.com.inatel.stockquotemanager.acceptance.steps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.com.inatel.stockquotemanager.controller.QuoteController;
import br.com.inatel.stockquotemanager.controller.form.StockQuoteForm;
import br.com.inatel.stockquotemanager.model.Quote;
import br.com.inatel.stockquotemanager.model.dto.StockDto;
import br.com.inatel.stockquotemanager.model.dto.StockQuotesDto;
import br.com.inatel.stockquotemanager.repository.QuoteRepository;
import br.com.inatel.stockquotemanager.service.StockService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuoteSteps {

	@Mock
	private StockService stockService;
	
	@Mock
	private QuoteRepository quoteRepository;
	
	private QuoteController quoteController;
	
	private ResponseEntity<StockQuotesDto> stockQuotesDto;
	
	private List<Quote> quotes;
	
	private StockQuoteForm form;
	
	@Given("The list of stocks from the external api stock manager")
	public void the_list_of_stocks_from_the_external_api_stock_manager() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(stockService.getStock("petr4")).thenReturn(new StockDto("petr4"));
		Mockito.when(stockService.getStock("vale5")).thenReturn(new StockDto("vale5"));
		Mockito.when(quoteRepository.findByStockId("petr4")).thenReturn(quotes("petr4"));		
		Mockito.when(quoteRepository.findByStockId("vale5")).thenReturn(quotes("vale5"));
		quoteController = new QuoteController(quoteRepository, stockService);
	}


	@When("I try to list the quotes of stock {string}")
	public void i_try_to_list_the_quotes_of_stock(String string) {
		stockQuotesDto = quoteController.listQuotesOfAStock(string);
	}
	
	@Then("The HTTP status code should be {int}")
	public void the_http_status_code_should_be(Integer int1) {
		Assert.assertEquals(int1, (Integer) stockQuotesDto.getStatusCode().value());
	}
	
	
	@Given("{int} quotes to be added in stock {string}")
	public void quotes_to_be_added_in_stock(Integer int1, String string) {
		quotes = generateQuotes(string, int1);
		form = new StockQuoteForm(string, convertQuotesToMap(quotes));
	}


	@When("I try to add the quotes")
	public void i_try_to_add_the_quotes() {
		stockQuotesDto = quoteController.addStockQuote(form);
	}
	
	private List<Quote> generateQuotes(String stockId, int numOfQuotes) {
		List<Quote> quotes = new ArrayList<Quote>();
		for(int i=0; i<numOfQuotes; i++) {
			Quote quote = new Quote(LocalDate.now().minusDays(i), new BigDecimal(i), stockId);
			quotes.add(quote);
		}
		return quotes;
	}
	
	private List<Quote> quotes(String stockId) {
		List<Quote> quotes = new ArrayList<Quote>();		
		Quote quote = new Quote(LocalDate.now(),new BigDecimal("10"),stockId);		
		quotes.add(quote);
		return quotes;
	}
	
	private Map<String, String> convertQuotesToMap(List<Quote> quoteList) {
		Map<String,String> quotesMap = new HashMap<String, String>();
		
		quoteList.forEach(quote -> {
			LocalDate date = quote.getDate();
			BigDecimal value = quote.getValue();
			
			quotesMap.put(date.toString(), value.toString());
		});
		
		return quotesMap;
	}
}
