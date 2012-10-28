package com.yubi.application.shop.paypal;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yubi.application.shop.Basket;

@Service
class PaypalServiceImpl implements PaypalService {
	
	private Logger logger = LoggerFactory.getLogger(PaypalServiceImpl.class);
	
	private String userName;
	
	private String password;
	
	private String signature;
	
	private String successURL;
	
	private String cancelURL;
	
	@Inject
	public PaypalServiceImpl(Environment env) {
		super();
		this.userName = env.getProperty(PaypalConstants.PAYPAL_USERNAME);
		this.password = env.getProperty(PaypalConstants.PAYPAL_PASSWORD);
		this.signature = env.getProperty(PaypalConstants.PAYPAL_SIGNATURE);
		this.successURL = env.getProperty(PaypalConstants.PAYPAL_SUCCESS_URL);
		this.cancelURL = env.getProperty(PaypalConstants.PAYPAL_CANCEL_URL);
	}

	/* (non-Javadoc)
	 * @see com.yubi.application.shop.paypal.PaypalService#setupTransaction()
	 */
	public String setupTransaction(Basket basket) {
		String request = 
				String.format(
						"USER=%s&" +
						"PWD=%s&" +
						"SIGNATURE=%s&" +
						"METHOD=SetExpressCheckout&" +
						"VERSION=72.0&" +
						"PAYMENTREQUEST_0_PAYMENTACTION=Sale&" +
						"PAYMENTREQUEST_0_AMT=10&" +
						"PAYMENTREQUEST_0_ITEMAMT=10&" +
						"RETURNURL=%s&" +
						"CANCELURL=%s&" +
						"SOLUTIONTYPE=Sole", 
						userName, password, signature, successURL, cancelURL);
		
		logger.info("Requesting token for request [" + request + "].");
		
		ResponseEntity<String> response = new RestTemplate().postForEntity("https://api-3t.sandbox.paypal.com/nvp", request, String.class);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		String[] result = StringUtils.split(response.getBody(), "&");
		
		for (String entry : result) {
			resultMap.put(StringUtils.split(entry, "=")[0], StringUtils.split(entry, "=")[1]);
		}
		
		logger.info("Express checkout request successful. With result map + [" + resultMap.toString() + "].");
		
		if (!resultMap.get("ACK").equals("Success")) {
			throw new RuntimeException("An error occurred creating the express checkout");
		}
		
		return resultMap.get("TOKEN");
	}

	public void completeTransaction() {
		// TODO Auto-generated method stub
	}
}
