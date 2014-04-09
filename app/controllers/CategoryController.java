package controllers;

import java.util.List;
import java.util.Map;

import models.Category;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;
import views.html.adminallcategories;
import views.html.allcategories;
import views.html.createcategory;
import views.html.created;

public class CategoryController extends Controller {

	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result createCategory() {
		
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String category = form.get("category")[0];
		String symbol = form.get("symbol")[0];
		
		boolean categoryIsEmpty = "".equals(category);
		boolean symbolIsEmpty = "".equals(symbol);
		
		if( categoryIsEmpty || symbolIsEmpty){
			
			flash().put("incomplete", "yes");
			
			return redirect(routes.CategoryController.createCategoryForm());
		}
		
		Category newCategory = new Category(category,symbol);
		
		JPA.em().persist(newCategory);	
		
		return ok(created.render("Category created"));
		
	}
	
	@Security.Authenticated(Secured.class)
	public static Result createCategoryForm() {
		return ok(createcategory.render(""));
	}

	@Transactional
	public static Result viewAllCategories() {
		
		List<Category> categories = JPA.em().createQuery("SELECT c FROM Category c", Category.class).getResultList();
				
		if(categories.isEmpty()){ 
			
			return ok(allcategories.render(categories,"No categories found"));
		}
		
		return ok(allcategories.render(categories,""));
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result deleteCategory(int id){
		
		Category foundCategory = JPA.em().find(Category.class, id);
		
		JPA.em().remove(foundCategory);
		
		return redirect(routes.CategoryController.adminAllCategories());
	}
	
	@Transactional
	@Security.Authenticated(Secured.class)
	public static Result adminAllCategories(){
		
		List<Category> categories = JPA.em().createQuery("SELECT c FROM Category c", Category.class).getResultList();
		
		return ok(adminallcategories.render(categories));
	}

}
