package br.com.inatel.stockquotemanager.model.dto;

public class StockDto {

	private String id;

	public StockDto(String id) {
		this.id = id;
	}
	
	public StockDto() {
		
	}
	
	public String getId() {
		return id;
	}
	
}
