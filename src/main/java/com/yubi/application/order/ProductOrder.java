package com.yubi.application.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ProductOrder {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Version
	private int version;
	
	@OneToMany(mappedBy = "id.order", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ProductOrderItem> items;
}
