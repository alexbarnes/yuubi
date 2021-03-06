package com.yubi.shop.paypal;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.Basket.BasketKey;
import com.yubi.shop.basket.BasketItem;
import com.yubi.shop.basket.BasketService;

/**
 * Service for calling Paypal at the various stages in the payment process.
 * 
 * @author Alex Barnes
 */
@Service
class PaypalServiceImpl implements PaypalService {

	private String userName;

	private String password;

	private String signature;

	private String successURL;

	private String cancelURL;

	private String paypalURL;

	private final BasketService basketService;

	private final PaypalRequestAccess paypalRequestAccess;

	@Inject
	public PaypalServiceImpl(Environment env, BasketService basketService, PaypalRequestAccess paypalRequestAccess) {
		super();
		this.userName = env.getProperty(PaypalConstants.PAYPAL_USERNAME);
		this.password = env.getProperty(PaypalConstants.PAYPAL_PASSWORD);
		this.signature = env.getProperty(PaypalConstants.PAYPAL_SIGNATURE);
		this.successURL = env.getProperty(PaypalConstants.PAYPAL_SUCCESS_URL);
		this.cancelURL = env.getProperty(PaypalConstants.PAYPAL_CANCEL_URL);
		this.paypalURL = env.getProperty(PaypalConstants.PAYPAL_API_ENDPOINT);
		this.basketService = basketService;
		this.paypalRequestAccess = paypalRequestAccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubi.application.shop.paypal.PaypalService#setupTransaction()
	 */
	public String setupTransaction(Basket basket, String sessionId, Currency currency) {

		String request = setupRequest(basket, currency).createRequest();

		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request);
		requestItem.setSessionId(sessionId);
		requestItem.setRequestType("SetExpressCheckout");
		paypalRequestAccess.save(requestItem);

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request, String.class);
		
		String decodedResponse;
		try {
			decodedResponse = URLDecoder.decode(response.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		Map<String, String> resultMap = createResultMap(decodedResponse);

		requestItem.setResponse(response.getBody());
		requestItem.setAck(resultMap.get("ACK"));
		requestItem.setTimeStampString(decode(resultMap.get("TIMESTAMP")));
		paypalRequestAccess.update(requestItem);

		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException(
					"An error occurred creating the express checkout. ACK ["
							+ resultMap.get("ACK") + "].");
		}

		// -- Add the token once we know for sure that we have one
		requestItem.setToken(resultMap.get("TOKEN"));
		paypalRequestAccess.update(requestItem);

		return resultMap.get("TOKEN");
	}

	public String completeTransaction(String token, String payerId,
			String sessionId, Basket basket, Currency currency, String orderReference) {
		
		DoExpressCheckoutRequestBuilder builder = new DoExpressCheckoutRequestBuilder(
				userName, password, signature);

		String request = builder.withPayerId(payerId).withToken(token)
				.withOrderTotal(basketService.getBasketTotal(basket, currency))
				.inCurrency(currency.getCurrencyCode())
				.withOrderReference(orderReference)
				.buildRequest();

		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request);
		requestItem.setSessionId(sessionId);
		requestItem.setRequestType("DoExpressCheckoutPayment");
		paypalRequestAccess.save(requestItem);
		// -- Transaction is now committed

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request, String.class);
		
		String decodedResponse;
		try {
			decodedResponse = URLDecoder.decode(response.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		Map<String, String> resultMap = createResultMap(decodedResponse);

		requestItem.setResponse(response.getBody());
		requestItem.setAck(resultMap.get("ACK"));
		requestItem.setTimeStampString(decode(resultMap.get("TIMESTAMP")));
		paypalRequestAccess.update(requestItem);
		// -- Transaction is now committed
		
		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException(
					"An error occurred creating the express checkout. ACK ["
							+ resultMap.get("ACK") + "].");
		}

		// Set the transaction id and token on the request record
		requestItem.setTransactionId(resultMap
				.get("PAYMENTINFO_0_TRANSACTIONID"));
		requestItem.setToken(resultMap.get("TOKEN"));
		paypalRequestAccess.update(requestItem);

		return requestItem.getTransactionId();
	}

	
	private SetupExpressTransactionRequestBuilder setupRequest(Basket basket, Currency currency) {
		SetupExpressTransactionRequestBuilder request = new SetupExpressTransactionRequestBuilder(
				userName, password, signature);

		request.withCancelURL(cancelURL).withReturnURL(successURL)
				.withShippingCost(basket.getDeliveryMethod().getCostInCurrency(currency))
				.withTotalCost(basketService.getBasketTotal(basket, currency)).inCurrency(currency.getCurrencyCode());
		
		if (basket.getDiscount() != null) {
			request.withDiscountType(basket.getDiscount().getDescription()).withDiscountAmount(basketService.getDiscountAmount(basket, currency));
		}

		// Add all of the basket items to the Paypal request
		for (Entry<BasketKey, BasketItem> item : basket.getItems().entrySet()) {

			ExpressTransactionItem checkoutItem;
				checkoutItem = new ExpressTransactionItem(
						item.getValue().getProductDescription(), StringUtils.EMPTY, item
								.getValue().getNumber(), item.getValue().getItemCost(currency), currency.getCurrencyCode());
			
			request.addItem(checkoutItem);
		}

		return request;
	}

	public ExpressCheckoutDetail loadTransactionDetail(String token,
			String sessionId) {
		GetExpressCheckoutDetailsRequest request = new GetExpressCheckoutDetailsRequest(
				token, userName, password, signature);
		
		// Save the request before we call the Paypal API
		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request.createRequest());
		requestItem.setSessionId(sessionId);
		requestItem.setRequestType("GetExpressCheckoutDetails");
		paypalRequestAccess.save(requestItem);

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request.createRequest(), String.class);
		
		String decodedResponse;
		try {
			decodedResponse = URLDecoder.decode(response.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		Map<String, String> resultMap = createResultMap(decodedResponse);
		
		// Record the result of calling the Paypal API
		requestItem.setResponse(response.getBody());
		requestItem.setTimeStampString(decode(resultMap.get("TIMESTAMP")));
		requestItem.setAck(resultMap.get("ACK"));
		paypalRequestAccess.update(requestItem);

		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException(
					"An error occurred creating the express checkout");
		}

		requestItem.setToken(resultMap.get("TOKEN"));
		paypalRequestAccess.update(requestItem);

		return ExpressCheckoutDetailBuilder.build(resultMap);
	}

	
	public PaypalTransaction loadTransaction(String transactionId) {
		String request = 
				new LoadTransactionRequestBuilder(userName, password, signature).withTransactionId(transactionId).buildRequest();
		
		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request, String.class);
		
		String decodedResponse;
		try {
			decodedResponse = URLDecoder.decode(response.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
		Map<String, String> resultMap = createResultMap(decodedResponse);
		
		// -- Build the result
		return new PaypalTransaction(
				resultMap.get("PAYMENTSTATUS"), 
				String.format("%s %s %s", 
						resultMap.get("SALUTATION") == null ? "" : resultMap.get("SALUTATION"), 
						resultMap.get("FIRSTNAME"), 
						resultMap.get("LASTNAME")), 
				resultMap.get("EMAIL"),
				resultMap.get("PENDINGREASON"),
				resultMap.get("NOTE"));
	}

	private Map<String, String> createResultMap(String response) {
		Map<String, String> resultMap = new HashMap<String, String>();

		Scanner scanner = new Scanner(response);
		try {
			scanner.useDelimiter("&");

			while (scanner.hasNext()) {
				String item = scanner.next();
				resultMap.put(StringUtils.split(item, "=")[0],
						StringUtils.split(item, "=")[1]);
			}
			
			return resultMap;
		} finally {
			scanner.close();
		}
	}
	
	private String decode(String toDecode) {
		try {
			return URLDecoder.decode(toDecode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
