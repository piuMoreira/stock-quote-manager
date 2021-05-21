Feature: Quote validation
  
  Background:
		Given The list of stocks from the external api stock manager
  
  Scenario Outline: List a Valid Stock Quote
  			AC 1 - List a Stack Quote.
  				AC 1.1 - With valid HTTP status code return.
    When I try to list the quotes of stock "<stockId>"
    Then The HTTP status code should be <statusCode>
    
    Examples:
			|stockId		|statusCode   |
			|petr4			|200					|
			|vale5			|200					|
			|zzzzz			|404					|
			|xxxxx			|404					|
			
    
	Scenario Outline: Add Quotes to a Valid Stock
				AC 2 - Add Quotes to a Valid Stock.
					AC 2.1 - Add one or more quotes.
					AC 2.1 - With valid HTTP status code return.					
				AC 3 - Refuse to add Invalid Stock Quote
					AC 3.1 - With Invalid Stock Id
		Given <numberOfQuotes> quotes to be added in stock "<stockId>"
		When I try to add the quotes
    Then The HTTP status code should be <statusCode>
    
    Examples:
    	|numberOfQuotes		|stockId		|statusCode		|
    	|1								|petr4			|201					|
    	|0								|petr4			|201					|
    	|9								|vale5			|201					|
    	|3								|vale5			|201					|
    	|4								|zzzzz			|404					|
    	|2								|xxxxx			|404					|
		