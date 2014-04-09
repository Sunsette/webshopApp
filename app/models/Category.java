package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String bootstrapSymbol;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category() {

	}

	public Category(int id, String name, String bootstrapSymbol) {
		this.id = id;
		this.name = name;
		this.bootstrapSymbol = bootstrapSymbol;
	}

	public Category(int id, Category other) {
		this(id, other.name, other.bootstrapSymbol);
	}

	public Category(String name, String bootstrapSymbol) {
		this.name = name;
		this.bootstrapSymbol = bootstrapSymbol;

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBootstrapSymbol() {
		return this.bootstrapSymbol;
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Category) {
			Category other = (Category) obj;
			return id == other.id;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("Id: %s, Name: %s, Bootstrap Symbol: %s", id,
				name, bootstrapSymbol);
	}

}
