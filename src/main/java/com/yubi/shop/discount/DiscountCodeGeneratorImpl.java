package com.yubi.shop.discount;

import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DiscountCodeGeneratorImpl implements DiscountCodeGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(DiscountCodeGenerator.class);
	
	private final DiscountAccess discountAccess;
	
	@Inject
	public DiscountCodeGeneratorImpl(DiscountAccess discountAccess) {
		super();
		this.discountAccess = discountAccess;
	}

	public String generateUniqueGiftVoucherCode() {
		String code = null;
		
		// Check this is unique. This should ever be a problem. Start off assuming it's a 
		// duplicate
		boolean duplicate = true;
		
		// This should only ever be run once. However, if we do find a duplicate we can try again.
		while(duplicate) {
			code = RandomStringUtils.randomAlphanumeric(10);
			Discount discount = discountAccess.get(code);
			
			if (discount == null)
				duplicate = false;
		}
		
		logger.info("Generated new voucher code with code [" + code + "].");
		
		return code;
		
	}
}
