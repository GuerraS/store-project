package com.ed.store.service;
import java.util.List;

import com.ed.store.entity.Category;
import com.ed.store.entity.Product;

public interface ProductService {
	public List<Product> listAllProduct();
	public Product getProduct(Long id);
	public Product createProduct(Product product);
	public Product updateProduct(Product product);
	public Product deleteProduct(Long id);
	public List<Product> findByCategory(Category category);
	public Product updateStock(Long id, Double quantity);
}

