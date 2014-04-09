package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import models.User;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminpage;
import views.html.createuser;
import views.html.loginrequest;
import views.html.mainpage;

public class UserController extends Controller{

	@Transactional
	public static Result createUser(){
		
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String email = form.get("email")[0];
		String password = form.get("password")[0];
		String firstname = form.get("firstname")[0];
		String lastname = form.get("lastname")[0];
		String address = form.get("address")[0];
		String town = form.get("town")[0];
		String postcode = form.get("postcode")[0];
		String telephone = form.get("telephone")[0];
		String dob = form.get("dob")[0];
		
		boolean emailIsEmpty = "".equals(email);
		boolean passwordIsEmpty = "".equals(password);
		boolean firstnameIsEmpty = "".equals(firstname);
		boolean lastnameIsEmpty = "".equals(lastname);
		boolean addressIsEmpty = "".equals(address);
		boolean townIsEmpty = "".equals(town);
		boolean postcodeIsEmpty = "".equals(postcode);
		boolean telephoneIsEmpty = "".equals(telephone);
		boolean dobIsEmpty = "".equals(dob);
		
		if(emailIsEmpty || passwordIsEmpty || firstnameIsEmpty ||  lastnameIsEmpty || addressIsEmpty || townIsEmpty ||
				postcodeIsEmpty || telephoneIsEmpty || dobIsEmpty ){
			flash().put("incomplete", "yes");
			
			return redirect(routes.UserController.createUserForm());
			
		}
		
		User newUser = new User(email, password,firstname,lastname,dob ,telephone ,address,town,postcode);
		
		JPA.em().persist(newUser);
		
		session().put("username", email);
		
		return ok(adminpage.render(""));
	}
	
	public static Result createUserForm(){
		
		return ok(createuser.render(""));
	}

	@Transactional
	public static Result logIn(){
		
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String email = form.get("email")[0];
		String password = form.get("password")[0];
		
		boolean emailIsEmpty = "".equals(email); 
		boolean passwordIsEmpty = "".equals(password);
		
		if(emailIsEmpty || passwordIsEmpty){
			if(emailIsEmpty){
				flash().put("username-empty", "yes");
			}
			if(passwordIsEmpty){
				flash().put("password-empty", "yes");
			}
			
			return redirect(routes.Application.index());
		}
		
		TypedQuery<User> query = JPA.em().createQuery( "SELECT c FROM User c WHERE c.email = :email AND c.password = :password", User.class);
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		List<User> matchingUsers = query.getResultList();
		
		if(matchingUsers.size() == 1){
			session().put("username", email);
			session().put("admin", "admin");
			
			return redirect(routes.Application.adminPage());
		} else {
			flash().put("no-username-password-match", "yes");
			
			return redirect(routes.Application.index());
		}
	}
	
	public static Result logOut(){
		
		session().clear();
		
		return ok(mainpage.render("Logged out"));
	}
	
	public static Result logInRequest(){
		
		return ok(loginrequest.render("Please log in to access this feature!"));
		
		}
	
}
