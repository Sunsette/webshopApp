package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private int id;
	private String productName;
	private String description;
	private double cost;
	private double rrp;
	private int inStockQuantity;
	private String imgUrl;

	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;

	@OneToMany(mappedBy = "product")
	private List<OrderDetails> orderDetails;

	@OneToMany(mappedBy = "product")
	private List<ShoppingCartItem> shoppingCart;

	public Product() {

	}

	public Product(int id, String productName, int inStockQuantity) {
		this.id = id;
		this.productName = productName;
		this.inStockQuantity = inStockQuantity;

	}

	public Product(int id, String productName, String description,
			int inStockQuantity) {
		this(id, productName, inStockQuantity);
		this.description = description;

	}

	public Product(int id, String productName, String description, double cost,
			double rrp, int inStockQuantity) {
		this(id, productName, description, inStockQuantity);
		this.cost = cost;
		this.rrp = rrp;

	}

	public Product(int id, String productName, String description, double cost,
			double rrp, int inStockQuantity, Category category) {
		this(id, productName, description, cost, rrp, inStockQuantity);
		this.category = category;

	}

	public Product(int id, String productName, String description, double cost,
			double rrp, int inStockQuantity, Category category, String imgUrl) {
		this(id, productName, description, cost, rrp, inStockQuantity);
		this.category = category;
		this.imgUrl = imgUrl;

	}

	public Product(String productName, String description, double cost,
			double rrp, int inStockQuantity, Category category) {
		this.productName = productName;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.inStockQuantity = inStockQuantity;
		this.category = category;

	}

	public Product(String productName, String description, double cost,
			double rrp, int inStockQuantity, Category category, String imgUrl) {
		this.productName = productName;
		this.description = description;
		this.cost = cost;
		this.rrp = rrp;
		this.inStockQuantity = inStockQuantity;
		this.category = category;
		this.imgUrl = imgUrl;

	}

	public Product(Product other, int inStockQuantity) {
		this.id = other.id;
		this.productName = other.productName;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
		this.inStockQuantity = inStockQuantity;
		this.category = other.category;
		this.imgUrl = other.imgUrl;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return this.productName;
	}

	public String getDescription() {
		return this.description;
	}

	public double getCost() {
		return this.cost;
	}

	public double getRrp() {
		return this.rrp;
	}

	public Category getCategory() {
		return this.category;
	}

	public int getInStockQuantity() {
		return this.inStockQuantity;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash *= productName.hashCode();
		hash *= description.hashCode();
		hash += cost;
		hash += rrp;
		hash *= inStockQuantity;

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof Product) {
			Product other = (Product) obj;

			boolean isEqual = true;
			isEqual = isEqual && other.productName.equals(this.productName);
			isEqual = isEqual && other.description.equals(this.description);
			isEqual = isEqual && other.cost == this.cost;
			isEqual = isEqual && other.rrp == this.rrp;
			isEqual = isEqual && other.inStockQuantity == this.inStockQuantity;

			return isEqual;

		}
		return false;
	}

	@Override
	public String toString() {
		return String
				.format("Id: %s, Name: %s, Description: %s, Cost: %s, RRP: %s, Quantity: %s",
						id, productName, description, cost, rrp,
						inStockQuantity);
	}

}
