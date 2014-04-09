package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private int id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String dob;
	private String telephone;
	private String address1;
	private String address2;
	private String town;
	private String postcode;

	@OneToMany(mappedBy = "user")
	List<Order> orders;

	@OneToMany(mappedBy = "user")
	List<ShoppingCartItem> shoppingCart;

	public User() {

	}

	public User(String email, String password, String firstname,
			String lastname, String address1, String town, String postcode) {
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address1 = address1;
		this.town = town;
		this.postcode = postcode;

	}

	public User(String email, String password, String firstname,
			String lastname, String dob, String telephone, String address1,
			String town, String postcode) {
		this(email, password, firstname, lastname, address1, town, postcode);
		this.dob = dob;

	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getDob() {
		return dob;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getTown() {
		return town;
	}

	public String getPostcode() {
		return postcode;
	}

	@Override
	public String toString() {
		return String
				.format("Id: %s, User: %s, Firstname: %s, Lastname: %s, Dob: %s, Telephone: %s, Address: %s %s %s %s ",
						getId(), getEmail(), getFirstname(), getLastname(),
						getDob(), getTelephone(), getAddress1(), getAddress2(),
						getTown(), getPostcode());
	}

	@Override
	public int hashCode() {
		int result = 1;
		result += 37 * id;
		result += 37 * this.getClass().hashCode();

		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (other instanceof User) {
			User otherUser = (User) other;
			boolean isSameClass = this.getClass().equals(otherUser.getClass());

			return (this.id == otherUser.id) && isSameClass;
		}

		return false;
	}

}
