package com.epam.interviews;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
	
	private List<Account> accounts = new ArrayList<Account>();
	private static Map<String, String> groups = new HashMap<String, String>();
	
	public void parseFile(String filename, String delimiter) {
		BufferedReader br = null;
		
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(filename));
			boolean firstLine = true;
			while ((currentLine = br.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
					continue;
				}
				String[] values = currentLine.split(delimiter, -1);
				
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
	
	public void run() {
		
		parseFile("FILE.DAT", "\\t");
		
		ExchangeRate.addCurrency("USD", 1.00);
		ExchangeRate.addCurrency("GBP", 1.654);
		ExchangeRate.addCurrency("CHF", 1.10);
		ExchangeRate.addCurrency("EUR", 1.35);
		
		Collections.sort(accounts, Account.COMPARATOR_LOGIC);
		double sum = 0;
		int counter = 0;
		Account previous = null;
		for (Account account : accounts) {
			if (account.getCountry() == null || account.getCountry().equals("")) {
				if (previous == null || (previous.getCity().equals(account.getCity()) && previous.getCreditRating().equals(account.getCreditRating()))) {
					previous = account;
					sum += account.getAmount();
					counter++;
				} else {
					Double newSum = sum/counter;
					newSum = (newSum * ExchangeRate.getRate(previous.getCurrency())) / ExchangeRate.getRate("EUR");
					if (previous.getCountry() == null || previous.getCountry().equals(""))
						groups.put(previous.getCity()+":"+previous.getCreditRating(), newSum.toString());
					else
						groups.put(previous.getCountry()+":"+previous.getCreditRating(), newSum.toString());
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
					newSum = (newSum * ExchangeRate.getRate(previous.getCurrency())) / ExchangeRate.getRate("EUR");
					if (previous.getCountry() == null || previous.getCountry().equals(""))
						groups.put(previous.getCity()+":"+previous.getCreditRating(), newSum.toString());
					else
						groups.put(previous.getCountry()+":"+previous.getCreditRating(), newSum.toString());
					sum = 0;
					counter = 0;
					previous = account;
					sum += account.getAmount();
					counter++;
				}
			}
		}
		
		for (String key : groups.keySet()) {
			String[] output = key.split(":");
			System.out.printf("Country/City : %s, Credit Rating : %s, Average : %s\n",
					output[0], output[1],
					ExchangeRate.getFormattedRate(Double.parseDouble(groups.get(key)), "EUR"));
		}
	}
	
}
