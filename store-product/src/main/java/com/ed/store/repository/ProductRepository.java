package com.ed.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.store.entity.Category;
import com.ed.store.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	public List<Product> findByCategory(Category category);
}
