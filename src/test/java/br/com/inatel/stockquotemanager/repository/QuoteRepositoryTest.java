package br.com.inatel.stockquotemanager.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.inatel.stockquotemanager.model.Quote;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class QuoteRepositoryTest {

	@Autowired
	private QuoteRepository quoteRepository;
	
	@Test
	public void shouldFindByStockId() {
		String stockId = "vale5";
		
		List<Quote> quotes = quoteRepository.findByStockId(stockId);
		
		Assert.assertNotNull(quotes);
		Assert.assertEquals(2, quotes.size());
	}
	
	@Test
	public void shouldNotFindQuoteOfAInexistentStock() {
		String stockId = "test";
		
		List<Quote> quotes = quoteRepository.findByStockId(stockId);
		
		Assert.assertNotNull(quotes);
		Assert.assertEquals(0, quotes.size());
	}
	
}
