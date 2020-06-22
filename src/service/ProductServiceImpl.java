package service;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements IProducService{
	private static Map<Integer, Product> productMap;

	static {
		productMap = new HashMap<>();
	}

	@Override
	public List<Product> findAll() {
		return new ArrayList<>(productMap.values());
	}

	@Override
	public Product findByID(int id) {
		return productMap.get(id);
	}

	@Override
	public void save(Product product) {
		productMap.put(product.getId(), product);
	}

	@Override
	public void remove(int id) {
		productMap.remove(id);
	}
}
