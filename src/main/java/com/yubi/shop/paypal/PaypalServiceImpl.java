package com.yubi.shop.paypal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yubi.application.product.Product;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketItem;

@Service
class PaypalServiceImpl implements PaypalService {
	
	private Logger logger = LoggerFactory.getLogger(PaypalServiceImpl.class);
	
	private String userName;
	
	private String password;
	
	private String signature;
	
	private String successURL;
	
	private String cancelURL;
	
	private String paypalURL;
	
	@Inject
	public PaypalServiceImpl(Environment env) {
		super();
		this.userName = env.getProperty(PaypalConstants.PAYPAL_USERNAME);
		this.password = env.getProperty(PaypalConstants.PAYPAL_PASSWORD);
		this.signature = env.getProperty(PaypalConstants.PAYPAL_SIGNATURE);
		this.successURL = env.getProperty(PaypalConstants.PAYPAL_SUCCESS_URL);
		this.cancelURL = env.getProperty(PaypalConstants.PAYPAL_CANCEL_URL);
		this.paypalURL = env.getProperty(PaypalConstants.PAYPAL_API_ENDPOINT);
	}

	/* (non-Javadoc)
	 * @see com.yubi.application.shop.paypal.PaypalService#setupTransaction()
	 */
	public String setupTransaction(Basket basket) {
		
		String request = setupRequest(basket).createRequest();
		
		logger.info(request);
		
		ResponseEntity<String> response = new RestTemplate().postForEntity(paypalURL, request, String.class);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String[] result = StringUtils.split(response.getBody(), "&");
		
		for (String entry : result) {
			resultMap.put(StringUtils.split(entry, "=")[0], StringUtils.split(entry, "=")[1]);
		}
		
		logger.info("Express checkout setup request successful. With result map + [" + resultMap.toString() + "].");
		
		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException("An error occurred creating the express checkout");
		}
		
		return resultMap.get("TOKEN");
	}

	public void completeTransaction(String token) {
		GetExpressCheckoutDetailsRequest request =
				new GetExpressCheckoutDetailsRequest(token);
		
		ResponseEntity<String> response = new RestTemplate().postForEntity(paypalURL, request.createRequest(), String.class);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String[] result = StringUtils.split(response.getBody(), "&");
		
		for (String entry : result) {
			resultMap.put(StringUtils.split(entry, "=")[0], StringUtils.split(entry, "=")[1]);
		}
		
		logger.info("Get express checkout details request result map + [" + resultMap.toString() + "].");
		
		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException("An error occurred creating the express checkout");
		}
	}
	
	
	private SetupExpressTransactionRequestBuilder setupRequest(Basket basket) {
		SetupExpressTransactionRequestBuilder request = 
				new SetupExpressTransactionRequestBuilder(userName, password, signature);
		
		request.
			withCancelURL(cancelURL).
			withReturnURL(successURL).
			withShippingCost(basket.getDeliveryMethod().getCost());
			
		// Add all of the basket items to the paypal request
		for (Entry<String, BasketItem> item : basket.getItems().entrySet()) {
			
			Product product = new Product();
			product.setDescription("Bracelet");
			product.setUnitPrice(new BigDecimal(35.00));
			
			ExpressTransactionItem checkoutItem = 
					new ExpressTransactionItem(
							product.getDescription(), 
							StringUtils.EMPTY, 
							item.getValue().getNumber(), 
							product.getUnitPrice());
			request.addItem(checkoutItem);
		}
		
		return request;
	}
}
