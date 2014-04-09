package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue
	private int id;
	private int quantityOfProducts;
	private double totalCost;
	private double profit;
	private String date;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@OneToMany(mappedBy = "order")
	private List<OrderDetails> orderDetails;

	public Order() {

	}

	public Order(int orderId, int quantityOfProducts, double totalCost,
			double profit, String date) {
		this.id = orderId;
		this.quantityOfProducts = quantityOfProducts;
		this.totalCost = totalCost;
		this.profit = profit;
		this.date = date;
	}

	public Order(int quantityOfProducts, double totalCost, double profit,
			String date, User user) {
		this.quantityOfProducts = quantityOfProducts;
		this.totalCost = totalCost;
		this.profit = profit;
		this.date = date;
		this.user = user;

	}

	public int getOrderId() {
		return id;
	}

	public int getQuantityOfProducts() {
		return quantityOfProducts;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public double getProfit() {
		return profit;
	}

	public String getDate() {
		return date;
	}

	@Override
	public int hashCode() {
		int hash = 37;
		hash *= id;
		hash *= quantityOfProducts;
		hash += quantityOfProducts;
		hash += profit;
		hash *= date.hashCode();

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Order) {
			Order other = (Order) obj;
			return id == other.id;
		}
		return false;
	}

	@Override
	public String toString() {

		return String
				.format("Order Id: %s, Quantity: %s, Total Cost: %s, Profit: %s, Date: %s",
						id, quantityOfProducts, totalCost, profit, date);
	}

}
