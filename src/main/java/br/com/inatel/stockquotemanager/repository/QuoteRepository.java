package br.com.inatel.stockquotemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.stockquotemanager.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

	List<Quote> findByStockId(String stockId);
	
}
