package controllers;

import play.mvc.*;
import views.html.*;
import play.mvc.Security;
import security.Secured;

public class Application extends Controller {

    public static Result index() {
        return ok(mainpage.render(""));
    }
    
    @Security.Authenticated(Secured.class)
    public static Result adminPage(){
    	
        	return ok(adminpage.render(""));

    }
    
    @Security.Authenticated(Secured.class)
    public static Result createOptions(){
    	return ok(admincreate.render(""));
    }
    
    @Security.Authenticated(Secured.class)
    public static Result editOptions(){
    	return ok(adminedit.render(""));
    }
    
    
}
