package com.epam.interviews;

import java.util.Comparator;

public class Account {

	private int companyCode;
	private long account;
	private String city;
	private String country;
	private String creditRating;
	private String currency;
	private double amount;
	
	public static final Comparator<Account> COMPARATOR_LOGIC = new CountryComparator();
	
	public Account (String companyCode, String account, String city, String country, String creditRating, String currency, String amount) {
		this.companyCode = Integer.parseInt(companyCode);
		this.account = Long.parseLong(account);
		this.city = city;
		this.country = country;
		this.creditRating = creditRating;
		this.currency = currency;
		this.amount = Double.parseDouble(amount);
	}

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public long getAccount() {
		return account;
	}

	public void setAccount(long account) {
		this.account = account;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String toString() {
		return this.getCompanyCode() + "|" +
				this.getAccount() + "|" +
				this.getCity() + "|" +
				this.getCountry() + "|" +
				this.getCreditRating() + "|" +
				this.getCurrency() + "|" +
				this.getAmount();
	}
	
	private static class CountryComparator implements Comparator<Account> {
		
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

}
