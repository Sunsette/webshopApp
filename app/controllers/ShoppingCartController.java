package controllers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.Product;
import models.ShoppingCartItem;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.created;
import views.html.shoppingcart;
import security.Secured;

public class ShoppingCartController extends Controller {

	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result addProductToShoppingCart(int id) {

		Map<String, String[]> form = request().body().asFormUrlEncoded();

		String quantity = form.get("quantity")[0];

		boolean quantityIsEmpty = "".equals(quantity);

		if (quantityIsEmpty) {
			flash().put("incomplete", "yes");
			return redirect(routes.ProductController.viewProduct(id));
		}

		Product product = JPA.em().find(Product.class, id);

		User currentUser = getCurrentUser();

		ShoppingCartItem newItem = new ShoppingCartItem(currentUser, product,
				Integer.parseInt(quantity));

		JPA.em().persist(newItem);

		return ok(created.render("Product had been added to you shopping cart"));
	}

	@Transactional
	public static User getCurrentUser() {

		String email = session().get("username");

		List<User> users = JPA.em()
				.createQuery("SELECT c FROM User c WHERE c.email = :email")
				.setParameter("email", email).getResultList();

		User currentUser = null;

		if (users.size() == 1) {
			currentUser = users.get(0);
		}
		return currentUser;
	}

	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result viewShoppingCart() {

		User currentUser = getCurrentUser();

		Map<Integer, Product> foundProducts = new TreeMap<>();

		int total = 0;
		
		double totalCost = 0;

		List<ShoppingCartItem> itemsInCart = null;

		if (currentUser != null) {

			itemsInCart = JPA
					.em()
					.createQuery(
							"SELECT c FROM ShoppingCartItem c WHERE c.user.id = :userId")
					.setParameter("userId", currentUser.getId())
					.getResultList();

			for (ShoppingCartItem item : itemsInCart) {

				foundProducts.put(item.getQuantity(), item.getProduct());
				total += item.getQuantity();
				totalCost += getCostOfItem(item) * item.getQuantity();
			}

		}

		return ok(shoppingcart.render(foundProducts, total, totalCost));
	}

	private static double getCostOfItem(ShoppingCartItem item) {
		
		Product foundProduct = JPA.em().find(Product.class, item.getProduct().getId());
		
		return foundProduct.getRrp();
	}
}
