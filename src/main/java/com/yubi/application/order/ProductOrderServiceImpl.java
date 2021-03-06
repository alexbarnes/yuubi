package com.yubi.application.order;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yubi.application.product.Product;
import com.yubi.application.product.ProductAccess;
import com.yubi.shop.basket.Basket;
import com.yubi.shop.basket.Basket.BasketKey;
import com.yubi.shop.basket.BasketItem;
import com.yubi.shop.basket.BasketService;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductOrderServiceImpl.class);
	
	private final ProductOrderAccess productOrderAccess;
	
	private final ProductAccess productAccess;
	
	private final BasketService basketService;
	
	@Inject
	public ProductOrderServiceImpl(
			ProductOrderAccess productOrderAccess,
			ProductAccess productAccess,
			BasketService basketService) {
		super();
		this.productOrderAccess = productOrderAccess;
		this.productAccess = productAccess;
		this.basketService = basketService;
	}


	@Transactional
	public ProductOrder createNewOrder(Basket basket, Currency currency) {
		log.info("New order created.");
		ProductOrder order = new ProductOrder();
		
		for (Entry<BasketKey, BasketItem> item : basket.getItems().entrySet()) {
			Product product = productAccess.load(item.getKey().getCode());
			ProductOrderItem orderLine = order.addItem(product);
			orderLine.setQuantity(item.getValue().getNumber());
			orderLine.setTotalCost(item.getValue().getLineCost(currency));
			
			// -- Remove the products from stock as well.
			product.setStockLevel(product.getStockLevel() - item.getValue().getNumber());
		}
		
		order.setCurrencyCode(currency.getCurrencyCode());
		order.setDelivery(basket.getDeliveryMethod());
		order.setDiscount(basket.getDiscount());
		order.setOrderTotal(basketService.getBasketTotal(basket, currency));
		order.setStatus(OrderStatus.ENTERED);
		order.setEnteredDate(new Date());
		
		long orderId = productOrderAccess.save(order);
		
		// -- Use the order id to generate a unique reference.
		LocalDate date = new LocalDate();
		order.setOrderReference(String.format("%d%d%d-%d", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), orderId));
		return order;
	}


	@Transactional
	public void updateTransactionId(long orderId, String transactionId) {
		ProductOrder order = productOrderAccess.get(orderId);
		order.setPaypalTransactionId(transactionId);
	}


	@Transactional
	public ProductOrder load(String reference) {
		ProductOrder order = productOrderAccess.loadByReference(reference);
		Hibernate.initialize(order.getItems());
		return order;
	}


	@Transactional
	public List<ProductOrder> recentOrders() {
		List<ProductOrder> list = productOrderAccess.recentOrders();
		for (ProductOrder order:list) {
			order.getItems().size();
		}
		return list;
	}


	@Transactional
	public boolean orderSent(long orderId) {
		ProductOrder order = productOrderAccess.get(orderId);
		if (order.getStatus() == OrderStatus.ENTERED) {
			order.setStatus(OrderStatus.SENT);
			return true;
		}
		return false;
	}
}