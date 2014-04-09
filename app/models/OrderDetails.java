package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderDetails {
	@Id
	private int id;
	private int quantity;

	@ManyToOne(cascade = CascadeType.ALL)
	private Order order;

	@ManyToOne(cascade = CascadeType.ALL)
	private Product product;

	OrderDetails() {

	}

	public OrderDetails(Order order, Product product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;

	}

	public int getQuantity() {
		return this.quantity;
	}

	public Order getOrder() {
		return this.order;
	}

	public Product getProduct() {
		return this.product;
	}

}
