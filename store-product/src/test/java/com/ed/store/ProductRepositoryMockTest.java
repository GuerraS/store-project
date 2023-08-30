package com.ed.store;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ed.store.entity.Category;
import com.ed.store.entity.Product;
import com.ed.store.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryMockTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void whenFindByCategory_thenReturnProductList() {
		Product product01 = Product.builder()
				.name("Computer")
				.category(Category.builder().id(1L).build())
				.description("")
				.stock(Double.parseDouble("10"))
				.price(Double.parseDouble("1240.90"))
				.status("Created")
				.createAt(new Date()).build();
		productRepository.save(product01);
		
		List<Product> founds = productRepository.findByCategory(product01.getCategory());
		System.out.println(founds.size());
		Assertions.assertThat(founds.size()).isEqualTo(3);
	}
}
