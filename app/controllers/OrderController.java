package controllers;

import java.util.Date;
import java.util.List;

import models.Order;
import models.OrderDetails;
import models.Product;
import models.ShoppingCartItem;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import views.html.created;
import views.html.order;

public class OrderController extends Controller{

	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result createOrder(){
		
		User currentUser = getCurrentUser();
		
		List<ShoppingCartItem> items = JPA.em().createQuery("SELECT c FROM ShoppingCartItem c WHERE c.user.id = :userId", ShoppingCartItem.class).setParameter("userId", currentUser.getId()).getResultList();
		
		Order order = buildOrder(currentUser, items);
		
		addOrderDetails(items, order);
		
		deleteFromShoppingCart(items);
		
		removeQuantityFromStock(items);
		
		return ok(created.render("Your order has been created"));
	}

	@Transactional
	private static void removeQuantityFromStock(List<ShoppingCartItem> items) {
		for(ShoppingCartItem item: items){
			
			int newQuantity = item.getProduct().getInStockQuantity() - item.getQuantity();
			 
			Product currentProduct = new Product(item.getProduct(),newQuantity);
			
			JPA.em().merge(currentProduct);
		}
		
	}

	@Transactional
	private static void deleteFromShoppingCart(List<ShoppingCartItem> items) {
		for(ShoppingCartItem item: items){
			JPA.em().remove(item);
		}
		
	}

	@Transactional
	private static void addOrderDetails(List<ShoppingCartItem> items, Order order) {
		for(ShoppingCartItem item: items){
			
			OrderDetails currentOrderDetail = new OrderDetails(order, item.getProduct(),item.getQuantity());
			
			JPA.em().persist(currentOrderDetail);
		}
	}
	
	@Transactional
	public static User getCurrentUser() {
		
		String email = session().get("username");
		
		List<User> users = JPA.em().createQuery("SELECT c FROM User c WHERE c.email = :email").setParameter("email", email).getResultList();
		
		User currentUser = null;
		
		if(users.size() == 1){
			currentUser = users.get(0);
		}
		return currentUser;
	}
	
	@Transactional
	public static Order buildOrder(User currentUser, List<ShoppingCartItem> items){
		
		int quantityOfProducts = 0;
		double totalCost = 0;
		double profit = 0;
		Date currentDate = new Date();
		String date = currentDate.toString();
		
		for(ShoppingCartItem item: items){
			quantityOfProducts += item.getQuantity();
			totalCost += item.getProduct().getCost();
			profit += item.getProduct().getRrp() - item.getProduct().getCost();
		}
		
		Order newOrder = new Order(quantityOfProducts,totalCost,profit, date, currentUser);
		
		JPA.em().persist(newOrder);
		
		return newOrder;
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result viewOrder(){
		
		User currentUser = getCurrentUser();
		
		List<Order> orders = JPA.em().createQuery("SELECT c FROM Order c WHERE c.user.id = :userId").setParameter("userId", currentUser.getId()).getResultList();
		
		return ok(order.render(orders));
	}
	
}
