package com.epam.interviews;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExchangeRateTester {

	@Test
	public void rateConversionTest() {
		ExchangeRate.addCurrency("EUR", 1.10);
		ExchangeRate.addCurrency("CHF", 1.06);
		ExchangeRate.addCurrency("USD", 1.00);
		
		double amount = 1;
		String sourceCurrency = "CHF";
		String targetCurrency = "EUR";
		double expected = 0.96;
		
		assertEquals(expected, ExchangeRate.getRate(sourceCurrency) * amount / ExchangeRate.getRate(targetCurrency), 0.005);
	}
}
