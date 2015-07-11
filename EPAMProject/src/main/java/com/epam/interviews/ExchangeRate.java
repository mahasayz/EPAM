package com.epam.interviews;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRate {

	private String name;
	private double rate;
	private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	private static Currency currentCurrency;
	private static ExchangeRate instance = null;

	private static Map<String, Double> rates = new HashMap<String, Double>();

	{
		currencyFormatter.setMaximumFractionDigits(2);
	}
	
	private ExchangeRate(String name, double rate) {
		this.name = name;
		this.rate = rate;
		rates.put(this.name, this.rate);
	}
	
	public static void addCurrency(String name, double rate) {
		instance = new ExchangeRate(name, rate);
	}

	public static double getRate(String name) {
		return rates.get(name);
	}
	
	public static String getFormattedRate(double rate, String currencyOutput) {
		currentCurrency = Currency.getInstance(currencyOutput);
		currencyFormatter.setCurrency(currentCurrency);
		return currencyFormatter.format(rate);
	}
}
