package com.yubi.application.order;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yubi.application.component.ComponentAccess;
import com.yubi.application.core.Model;
import com.yubi.application.core.ScreenMode;
import com.yubi.application.supplier.SupplierAccess;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	private static final String CURRENT_ORDER = "current_order";
	
	private final OrderAccess orderAccess;
	
	private final OrderService orderService;
	
	private final SupplierAccess supplierAccess;
	
	private final ComponentAccess componentAccess;
	
	@Inject
	public OrderController(OrderAccess orderAccess, OrderService orderService,
			ComponentAccess componentAccess, SupplierAccess supplierAccess) {
		super();
		this.orderAccess = orderAccess;
		this.orderService = orderService;
		this.componentAccess = componentAccess;
		this.supplierAccess = supplierAccess;
	}

	@RequestMapping("/add")
	public Model createOrder(HttpSession session) {
		ComponentOrder order = new ComponentOrder();
		session.setAttribute(CURRENT_ORDER, order);
		return new Model(ScreenMode.CREATE, "order", "order", order);
	}

	@RequestMapping("/edit/{id}")
	public Model editOrder(@PathVariable(value = "id") long id, HttpSession session) {
		ComponentOrder order = orderAccess.load(id);
		
		// Set up the component ids so that the view knows what to show
		for (OrderItem item : order.getItems()) {
			item.setComponentId(item.getId().getComponent().getId());
		}
		
		order.setSupplierId(order.getSupplier().getId());
		
		session.setAttribute(CURRENT_ORDER, order);

		return new Model(ScreenMode.UPDATE, "order", "order", order);
	}

	@RequestMapping("/view/{id}")
	public Model viewOrder(@PathVariable(value = "id") long id,
			HttpSession session) {
		ComponentOrder order = orderAccess.load(id);
		
		session.setAttribute(CURRENT_ORDER, order);
		
		return new Model(ScreenMode.ENQUIRE, "order", "order", order);
	}

	@RequestMapping("/items")
	public Model components(String screenMode, HttpSession session) {
		ComponentOrder order = (ComponentOrder) session.getAttribute(CURRENT_ORDER);

		return new Model(ScreenMode.valueOf(screenMode), "orderform",
				"order", order);
	}

	@RequestMapping("/item/add")
	public Model addComponent(@ModelAttribute("order") ComponentOrder order,
			String screenMode) {

		// Set objects on the order to reflect the ids set from the
		// UI.
		for (OrderItem item : order.getItems()) {
			item.getId().setComponent(
					componentAccess.load(item.getComponentId()));
		}
		
		order.setSupplier(supplierAccess.load(order.getSupplierId()));

		order.getItems().add(new OrderItem());
		return new Model(ScreenMode.valueOf(screenMode), "orderform",
				"order", order);
	}

	@RequestMapping("/item/remove/{index}")
	public Model removeComponent(@ModelAttribute("order") ComponentOrder order,
			@PathVariable(value = "index") int index, String screenMode) {

		order.getItems().remove(index);

		// For the remaining components set the actual objects
		for (OrderItem item : order.getItems()) {
			item.getId().setComponent(
					componentAccess.load(item.getComponentId()));
		}
		
		order.setSupplier(supplierAccess.load(order.getSupplierId()));

		return new Model(ScreenMode.valueOf(screenMode), "orderform",
				"order", order);
	}

	@RequestMapping("/save")
	public Model save(@ModelAttribute("order") @Valid ComponentOrder order,
			BindingResult result, String screenMode, HttpSession session) {

		// If there are errors return the page
		if (result.hasErrors()) {
			return new Model(ScreenMode.valueOf(screenMode), "order",
					"order", order);
		}
		
		ComponentOrder sessionOrder = (ComponentOrder) session.getAttribute(CURRENT_ORDER);
		
		mapViewOrderToSession(sessionOrder, order);
		
		orderService.saveOrder(sessionOrder);
		return new Model("redirect:/order/view/" + sessionOrder.getId());
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpSession session, String screenMode, String code) {
		session.setAttribute(CURRENT_ORDER, null);
		
		if (ScreenMode.valueOf(screenMode) == ScreenMode.CREATE) {
			return "redirect:/home";
		} else {
			return "redirect:/order/view/" + code;
		}
	}
	
	
	private void mapViewOrderToSession(ComponentOrder sessionOrder, ComponentOrder viewOrder) {
		sessionOrder.setDescription(viewOrder.getDescription());
		sessionOrder.setDeliveryDate(viewOrder.getDeliveryDate());
		sessionOrder.setOrderDate(viewOrder.getOrderDate());
		sessionOrder.setSupplier(supplierAccess.load(viewOrder.getSupplierId()));
		sessionOrder.setSupplierOrderNumber(viewOrder.getSupplierOrderNumber());
		
		// Handle the components by detatching all and then adding back
		sessionOrder.getItems().clear();
		
		for (OrderItem item : viewOrder.getItems()) {
			OrderItem addedItem = sessionOrder.addItem();
			addedItem.getId().setComponent(
					componentAccess.load(item.getComponentId()));
			addedItem.setNumber(item.getNumber());
			addedItem.setCost(item.getCost());
			addedItem.setNumberReceived(item.getNumberReceived());
		}
	}
}
