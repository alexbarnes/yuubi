package com.yubi.shop.paypal;

import java.util.Map;

public class ExpressCheckoutDetailBuilder {
	
	private static final String CUSTOMER_NAME = "PAYMENTREQUEST_0_SHIPTONAME";
	private static final String ADDRESS_LINE_1 = "PAYMENTREQUEST_0_SHIPTOSTREET";
	private static final String ADDRESS_LINE_2 = "PAYMENTREQUEST_0_SHIPTOSTREET2";
	private static final String ADDRESS_CITY = "PAYMENTREQUEST_0_SHIPTOCITY";
	private static final String ADDRESS_COUNTY = "PAYMENTREQUEST_0_SHIPTOSTATE";
	private static final String ADDRESS_POST_CODE = "PAYMENTREQUEST_0_SHIPTOZIP";
	private static final String ADDRESS_COUNTRY = "PAYMENTREQUEST_0_SHIPTOCOUNTRYNAME";
	private static final String MESSAGE = "PAYMENTREQUEST_0_NOTETEXT";
	
	
	public static ExpressCheckoutDetail build(Map<String, String> result) {
		ExpressCheckoutDetail detail = new ExpressCheckoutDetail();
		
		detail.getCustomer().setName(result.get(CUSTOMER_NAME));
		detail.getCustomer().setAddressLine1(result.get(ADDRESS_LINE_1));
		detail.getCustomer().setAddressLine2(result.get(ADDRESS_LINE_2));
		detail.getCustomer().setCounty(result.get(ADDRESS_COUNTY));
		detail.getCustomer().setCity(result.get(ADDRESS_CITY));
		detail.getCustomer().setPostCode(result.get(ADDRESS_POST_CODE));
		detail.getCustomer().setCountry(result.get(ADDRESS_COUNTRY));
		detail.setMessage(result.get(MESSAGE));
		return detail;
	}
}