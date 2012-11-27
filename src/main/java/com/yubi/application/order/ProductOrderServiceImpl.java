package com.yubi.application.order;

import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.ProductAccess;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.BasketItem;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
	
	private final ProductOrderAccess productOrderAccess;
	
	private final ProductAccess productAccess;
	
	@Inject
	public ProductOrderServiceImpl(
			ProductOrderAccess productOrderAccess,
			ProductAccess productAccess) {
		super();
		this.productOrderAccess = productOrderAccess;
		this.productAccess = productAccess;
	}


	@Transactional
	public long createNewOrder(Basket basket, String transactionId) {
		ProductOrder order = new ProductOrder();
		
		for (Entry<String, BasketItem> item : basket.getItems().entrySet()) {
			ProductOrderItem orderLine = order.addItem(productAccess.load(item.getKey()));
			orderLine.setQuantity(item.getValue().getNumber());
			orderLine.setTotalCost(item.getValue().getTotalCost());
		}
		order.setPaypalTransactionId(transactionId);
		return productOrderAccess.save(order);
	}

}
