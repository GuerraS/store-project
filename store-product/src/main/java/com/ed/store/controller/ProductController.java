package com.ed.store.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ed.store.entity.Category;
import com.ed.store.entity.Product;
import com.ed.store.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductService productService; 
	
	@GetMapping
	public ResponseEntity<List<Product>> ListProducts(@RequestParam(name="categoryId", required = false) Long categoryId){
		List<Product> products = new ArrayList<Product>();
		if(null == categoryId) {
			products = productService.listAllProduct();
			if (products.isEmpty())
				return ResponseEntity.noContent().build();
		}else {
			products = productService.findByCategory(Category.builder().id(categoryId).build());
			if (products.isEmpty())
				return ResponseEntity.notFound().build();
		}
		
		
		return ResponseEntity.ok(products);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id){
		Product products = productService.getProduct(id);
		if (null == products)
			return ResponseEntity.noContent().build();	
		return ResponseEntity.ok(products);
	}
	
	// corregir los update e insert con un bad request si es qye fallan
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result) {
		if(result.hasErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage.formatMessaage(result) );
		}
		
		Product productCreate = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	};
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product product) {
		product.setId(id);
		Product updateProduct = productService.updateProduct(product);
		if (null == updateProduct)
			return ResponseEntity.notFound().build();	
		return ResponseEntity.ok(product);
	};
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") Long id) {
		Product updateProduct = productService.deleteProduct(id);
		if (null == updateProduct)
			return ResponseEntity.notFound().build();	
		return ResponseEntity.ok(updateProduct);
	};
	
	@PutMapping(value="/{id}/stock")
	public ResponseEntity<Product> updateStock(@PathVariable(value = "id") Long id, @RequestParam(name = "quantity") Double quantity) {
		Product updateProduct = productService.updateStock(id, quantity);
		if (null == updateProduct)
			return ResponseEntity.notFound().build();	
		return ResponseEntity.ok(updateProduct);
	};
//	public List<Product> findByCategory(Category category);
//	public Product updateStock(Long id, Double quantity);
}
