package com.epam.interviews;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		Collections.sort(parser.accounts, new CountryComparator());
		double sum = 0;
		int counter = 0;
		String previous = null;
		for (Account account : parser.accounts) {
			if (account.getCountry() == null || account.getCountry().equals("")) {
				if (previous == null || previous.equals(account.getCity()+account.getCreditRating())) {
					System.out.println("NEW/SAME City "+account.getCity()+"|CreditRating "+account.getCreditRating()+"|Sum "+sum+"|Counter "+counter);
					previous = account.getCity()+account.getCreditRating();
					sum += account.getAmount();
					counter++;
				} else {
					System.out.println("DIFF City "+account.getCity()+"|CreditRating "+account.getCreditRating()+"|Sum "+sum+"|Counter "+counter);
					Double newSum = sum/counter;
					System.out.println("Sum was " + newSum);
					groups.put(previous, newSum.toString());
					counter = 0;
					sum = 0;
					previous = account.getCity()+account.getCreditRating();
					sum += account.getAmount();
					counter++;
				}
			} else {
				if (previous == null || previous.equals(account.getCountry()+account.getCreditRating())) {
					System.out.println("NEW/SAME Country "+account.getCountry()+"|CreditRating "+account.getCreditRating()+"|Sum "+sum+"|Counter "+counter);
					previous = account.getCountry()+account.getCreditRating();
					sum += account.getAmount();
					counter++;
				} else {
					System.out.println("DIFF Country "+account.getCountry()+"|CreditRating "+account.getCreditRating()+"|Sum "+sum+"|Counter "+counter);
					Double newSum = sum/counter;
					System.out.println("Sum was " + newSum);
					groups.put(previous, newSum.toString());
					sum = 0;
					counter = 0;
					previous = account.getCountry()+account.getCreditRating();
					sum += account.getAmount();
					counter++;
				}
			}
		}
		
		for (String key : groups.keySet()) {
			System.out.println(key + " = " + groups.get(key));
		}
		
	}
	
}
