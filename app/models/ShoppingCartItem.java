package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ShoppingCartItem {

	@Id
	@GeneratedValue
	int id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Product product;

	private int quantity;

	public ShoppingCartItem() {

	}

	public ShoppingCartItem(User user, Product product, int quantity) {
		this.user = user;
		this.product = product;
		this.quantity = quantity;

	}

	public User getUser() {
		return this.user;
	}

	public Product getProduct() {
		return this.product;
	}

	public int getQuantity() {
		return this.quantity;
	}

}
