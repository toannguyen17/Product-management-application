package service;

import model.Product;

import java.util.List;

public interface IProducService {
	List<Product> findAll();
	Product findByID(int id);

	void save(Product product);
	void remove(int id);
}
