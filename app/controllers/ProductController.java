package controllers;

import java.util.List;

import models.Category;
import models.Product;

import java.util.Map;

import javax.persistence.TypedQuery;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminallproducts;
import views.html.allproducts;
import views.html.created;
import views.html.createproduct;
import views.html.product;
import views.html.productsincategory;
import security.Secured;

public class ProductController extends Controller{
	
	@Transactional
	@Security.Authenticated
	public static Result createProduct(){
		
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String productName = form.get("productName")[0];
		String description = form.get("description")[0];
		String category = form.get("category")[0];
		String cost = form.get("cost")[0];
		String rrp = form.get("rrp")[0];
		String quantity = form.get("quantity")[0];
		String imgUrl = form.get("imgUrl")[0];
		
		boolean productNameIsEmpty = "".equals(productName);
		boolean descriptionIsEmpty = "".equals(description);
		boolean categoryIsEmpty = "".equals(category);
		boolean costIsEmpty = "".equals(cost);
		boolean rrpIsEmpty = "".equals(rrp);
		boolean quantityIsEmpty = "".equals(quantity);
		boolean imgUrlIsEmpty = "".equals(imgUrl);
		
		if(productNameIsEmpty || descriptionIsEmpty || categoryIsEmpty || costIsEmpty || rrpIsEmpty ||quantityIsEmpty || imgUrlIsEmpty ){
			flash().put("incomplete", "yes");
			
			return redirect(routes.ProductController.createProductForm());
		}
			
		Category foundCategory = getCategory(category);
		
		Product newProduct = new Product(productName, description,Double.parseDouble(cost), Double.parseDouble(rrp),Integer.parseInt(quantity),foundCategory, imgUrl);
		
		JPA.em().persist(newProduct);
		
		
		return ok(created.render("Product created"));
	}

	private static Category getCategory(String category) {
		
		List<Category> categories = JPA.em().createQuery("SELECT c FROM Category c WHERE c.name = :categoryName", Category.class).setParameter("categoryName", category).getResultList();
		
		Category foundCategory = null;
		
		if(categories.size() != 0){
		
			foundCategory = categories.get(0);

		}
		return foundCategory;
	}
	
	@Security.Authenticated
	public static Result createProductForm(){
		
		return ok(createproduct.render(""));
	}
	
	@Transactional
	public static Result viewProduct(int id){
		
		Product foundProduct = JPA.em().find(Product.class, id);
			
		return ok(product.render(foundProduct));
	}
	
	@Transactional
	public static Result viewAllProducts(){
		
		TypedQuery<Product> query = JPA.em().createQuery("SELECT c FROM Product c", Product.class);
		
		List<Product> foundProducts = query.getResultList();
		
		return ok(allproducts.render(foundProducts));
	}
	
	@Transactional
	public static Result viewProductsOfXCategory(String category){
		
		List<Product> foundProducts = null;
		
		Category foundCategory = getCategory(category);
			
		foundProducts = JPA.em().createQuery("SELECT c FROM Product c WHERE c.category.id = :categoryId", Product.class).setParameter("categoryId", foundCategory.getId()).getResultList();
		
		if(foundProducts.isEmpty()){ 
			return ok(productsincategory.render(foundProducts, "No products found"));
		}
		
		return ok(productsincategory.render(foundProducts, category));
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result deleteProduct(int productId){
		
		int query = JPA.em().createQuery("DELETE FROM OrderDetails c WHERE c.product.id = :productId").setParameter("productId", productId).executeUpdate();
		
		Product product = JPA.em().find(Product.class, productId);
		
		JPA.em().remove(product);
		
		return redirect(routes.ProductController.adminAllProducts());
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result adminAllProducts(){
		
		TypedQuery<Product> query = JPA.em().createQuery("SELECT c FROM Product c", Product.class);
		
		List<Product> foundProducts = query.getResultList();
		
		return ok(adminallproducts.render(foundProducts));
	}

}
