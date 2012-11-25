package com.yubi.shop.paypal;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketItem;
import com.yubi.shop.basket.BasketService;

@Service
class PaypalServiceImpl implements PaypalService {

	private String userName;

	private String password;

	private String signature;

	private String successURL;

	private String cancelURL;

	private String paypalURL;

	private final ProductAccess productAccess;

	private final BasketService basketService;

	private final PaypalRequestAccess paypalRequestAccess;

	@Inject
	public PaypalServiceImpl(Environment env, ProductAccess productAccess,
			BasketService basketService, PaypalRequestAccess paypalRequestAccess) {
		super();
		this.userName = env.getProperty(PaypalConstants.PAYPAL_USERNAME);
		this.password = env.getProperty(PaypalConstants.PAYPAL_PASSWORD);
		this.signature = env.getProperty(PaypalConstants.PAYPAL_SIGNATURE);
		this.successURL = env.getProperty(PaypalConstants.PAYPAL_SUCCESS_URL);
		this.cancelURL = env.getProperty(PaypalConstants.PAYPAL_CANCEL_URL);
		this.paypalURL = env.getProperty(PaypalConstants.PAYPAL_API_ENDPOINT);
		this.productAccess = productAccess;
		this.basketService = basketService;
		this.paypalRequestAccess = paypalRequestAccess;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yubi.application.shop.paypal.PaypalService#setupTransaction()
	 */
	public String setupTransaction(Basket basket, String sessionId) {

		String request = setupRequest(basket).createRequest();

		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request);
		requestItem.setSessionId(sessionId);

		paypalRequestAccess.save(requestItem);

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request, String.class);

		Map<String, String> resultMap = createResultMap(response.getBody());

		requestItem.setResponse(response.getBody());
		requestItem.setAck(resultMap.get("ACK"));
		requestItem.setTimeStampString(decode(resultMap.get("TIMESTAMP")));
		paypalRequestAccess.update(requestItem);

		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException(
					"An error occurred creating the express checkout. ACK ["
							+ resultMap.get("ACK") + "].");
		}

		// Add the token once we know for sure that we have one (ACK=Success)
		requestItem.setToken(resultMap.get("TOKEN"));
		paypalRequestAccess.update(requestItem);

		String token;
		try {
			token = URLDecoder.decode(resultMap.get("TOKEN"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}

		return token;
	}

	public String completeTransaction(String token, String payerId,
			String sessionId, Basket basket) {
		DoExpressCheckoutRequestBuilder builder = new DoExpressCheckoutRequestBuilder(
				userName, password, signature);

		String request = builder.withPayerId(payerId).withToken(token)
				.withOrderTotal(basketService.getBasketTotal(basket))
				.buildRequest();

		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request);
		requestItem.setSessionId(sessionId);
		paypalRequestAccess.save(requestItem);

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request, String.class);

		Map<String, String> resultMap = createResultMap(response.getBody());

		requestItem.setResponse(response.getBody());
		requestItem.setAck(resultMap.get("ACK"));
		requestItem.setTimeStampString(decode(resultMap.get("TIMESTAMP")));
		paypalRequestAccess.update(requestItem);

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

	private SetupExpressTransactionRequestBuilder setupRequest(Basket basket) {
		SetupExpressTransactionRequestBuilder request = new SetupExpressTransactionRequestBuilder(
				userName, password, signature);

		request.withCancelURL(cancelURL).withReturnURL(successURL)
				.withShippingCost(basket.getDeliveryMethod().getCost())
				.withTotalCost(basketService.getBasketTotal(basket));

		// Add all of the basket items to the Paypal request
		for (Entry<String, BasketItem> item : basket.getItems().entrySet()) {

			Product product = productAccess.load(item.getValue()
					.getProductCode());
			ExpressTransactionItem checkoutItem = new ExpressTransactionItem(
					product.getDescription(), StringUtils.EMPTY, item
							.getValue().getNumber(), product.getUnitPrice());
			request.addItem(checkoutItem);
		}

		return request;
	}

	public PaypalTransactionDetails loadTransactionDetail(String token,
			String sessionId) {
		GetExpressCheckoutDetailsRequest request = new GetExpressCheckoutDetailsRequest(
				token, userName, password, signature);
		
		// Save the request before we call the Paypal API
		PaypalRequest requestItem = new PaypalRequest();
		requestItem.setRequest(request.createRequest());
		requestItem.setSessionId(sessionId);
		paypalRequestAccess.save(requestItem);

		ResponseEntity<String> response = new RestTemplate().postForEntity(
				paypalURL, request.createRequest(), String.class);

		Map<String, String> resultMap = createResultMap(response.getBody());
		
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

		return new PaypalTransactionDetails();
	}

	public void loadTransaction(String transactionId) {

	}

	private Map<String, String> createResultMap(String response) {
		Map<String, String> resultMap = new HashMap<String, String>();

		Scanner scanner = new Scanner(response);
		scanner.useDelimiter("&");

		while (scanner.hasNext()) {
			String item = scanner.next();
			resultMap.put(StringUtils.split(item, "=")[0],
					StringUtils.split(item, "=")[1]);
		}
		return resultMap;
	}
	
	private String decode(String toDecode) {
		try {
			return URLDecoder.decode(toDecode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
