package com.epam.interviews;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CountryComparator implements Comparator<Account> {

	@Override
	public int compare(Account o1, Account o2) {
		// TODO Auto-generated method stub
		if (o1.getCountry() != null && o2.getCountry() != null) {
			if (o1.getCountry().equals(o2.getCountry()))
				return o1.getCreditRating().compareTo(o2.getCreditRating());
			else
				return o1.getCountry().compareTo(o2.getCountry());
		} else {
			if (o1.getCity().equals(o2.getCity()))
				return o1.getCreditRating().compareTo(o2.getCreditRating());
			else
				return o1.getCity().compareTo(o2.getCity());
		}
	}
	
}

public class Parser {
	
	private List<Account> accounts = new ArrayList<Account>();
	private static Map<String, String> groups = new HashMap<String, String>();
	private static Map<String, Double> currencyMap =
			new HashMap<String, Double>();
	
	
	
	public void parseFile(String filename) {
		BufferedReader br = null;
		
		try {
			String currentLine;
			br = new BufferedReader(new FileReader("FILE.DAT"));
			boolean firstLine = true;
			while ((currentLine = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				String[] values = currentLine.split("\\t", -1);
				
				accounts.add(new Account(values[0], values[1], values[2], 
						values[3], values[4], values[5], values[6]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.parseFile("");

		/*new Currency("USD", 1.00),
			new Currency("GBP", 1.654),
			new Currency("CHF", 1.10),
			new Currency("EUR", 1.35)
	};*/
		currencyMap.put("USD", 1.00);
		currencyMap.put("GBP", 1.654);
		currencyMap.put("CHF", 1.10);
		currencyMap.put("EUR", 1.35);
		
		Collections.sort(parser.accounts, new CountryComparator());
		double sum = 0;
		int counter = 0;
		Account previous = null;
		for (Account account : parser.accounts) {
			if (account.getCountry() == null || account.getCountry().equals("")) {
				if (previous == null || (previous.getCity().equals(account.getCity()) && previous.getCreditRating().equals(account.getCreditRating()))) {
					previous = account;
					sum += account.getAmount();
					counter++;
				} else {
					Double newSum = sum/counter;
					newSum = (newSum * currencyMap.get(previous.getCurrency())) / currencyMap.get("EUR");
					if (previous.getCountry() == null || previous.getCountry().equals(""))
						groups.put(previous.getCity()+"|"+previous.getCreditRating(), newSum.toString());
					else
						groups.put(previous.getCountry()+"|"+previous.getCreditRating(), newSum.toString());
					counter = 0;
					sum = 0;
					previous = account;
					sum += account.getAmount();
					counter++;
				}
			} else {
				if (previous == null || (previous.getCountry().equals(account.getCountry()) && previous.getCreditRating().equals(account.getCreditRating()))) {
					previous = account;
					sum += account.getAmount();
					counter++;
				} else {
					Double newSum = sum/counter;
					newSum = (newSum * currencyMap.get(previous.getCurrency())) / currencyMap.get("EUR");
					if (previous.getCountry() == null || previous.getCountry().equals(""))
						groups.put(previous.getCity()+"|"+previous.getCreditRating(), newSum.toString());
					else
						groups.put(previous.getCountry()+"|"+previous.getCreditRating(), newSum.toString());
					sum = 0;
					counter = 0;
					previous = account;
					sum += account.getAmount();
					counter++;
				}
			}
		}
		
		for (String key : groups.keySet()) {
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
			Currency currentCurrency = Currency.getInstance("EUR");
			currencyFormatter.setCurrency(currentCurrency);
			currencyFormatter.setMaximumFractionDigits(2);
			Double value = Double.parseDouble(groups.get(key));
			System.out.println(key + " = " + currencyFormatter.format(value));
		}
		
	}
	
}
