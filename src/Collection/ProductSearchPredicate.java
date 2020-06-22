package Collection;

import model.Product;

import java.util.function.Predicate;

public class ProductSearchPredicate implements Predicate<Product> {
	String check;

	public void setCheck(String check) {
		this.check = check;
	}

	@Override
	public boolean test(Product product) {
		int check = product.getName().compareTo(this.check);
		check = Math.abs(check);

		System.out.println(check);

		if (check < 10) return true;
		return false;
	}
}
