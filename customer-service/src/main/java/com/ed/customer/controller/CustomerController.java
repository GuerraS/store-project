package com.ed.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ed.customer.entity.Customer;
import com.ed.customer.entity.Region;
import com.ed.customer.service.CustomerService;
import com.ed.customer.utils.ErrorMessage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	// -------------------Retrieve All Customers/By region
	// id--------------------------------------------

	@GetMapping
	public ResponseEntity<List<Customer>> ListCustomer(
			@RequestParam(name = "regionId", required = false) Long regionId) {
		List<Customer> customers = new ArrayList<Customer>();
		if (null == regionId) {
			customers = customerService.listAllCustomers();
			if (customers.isEmpty())
				return ResponseEntity.noContent().build();
		} else {
			Region Region = new Region();
			Region.setId(regionId);
			customers = customerService.findCustomersByRegion(Region);
			if (null == customers) {
				log.error("Customers with Region id {} not found.", regionId);
				return ResponseEntity.notFound().build();
			}
		}

		return ResponseEntity.ok(customers);
	}

	// -------------------Retrieve Single
	// Customer------------------------------------------
	@GetMapping(value = "/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable(value = "id") Long id) {
		Customer customer = customerService.getCustomer(id);
		if (null == customer) {
			log.error("Customer with id {} not found.", id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customer);
	}

	// -------------------Create a
	// Customer-------------------------------------------

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
		log.info("Creating Customer : {}", customer);
		if (result.hasErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			String error = errorMessage.formatMessaage(result);
			log.error("Json with errors", error);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
		}

		Customer customerDB = customerService.createCustomer(customer);

		return ResponseEntity.status(HttpStatus.CREATED).body(customerDB);
	}

	// ------------------- Update a Customer
	// ------------------------------------------------

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		log.info("Updating Customer with id {}", id);

		Customer currentCustomer = customerService.getCustomer(id);

		if (null == currentCustomer) {
			log.error("Unable to update. Customer with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		customer.setId(id);
		currentCustomer = customerService.updateCustomer(customer);
		return ResponseEntity.ok(currentCustomer);
	}

	// ------------------- Delete a
	// Customer-----------------------------------------

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
		log.info("Fetching & Deleting Customer with id {}", id);

		Customer customer = customerService.getCustomer(id);
		if (null == customer) {
			log.error("Unable to delete. Customer with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		customer = customerService.deleteCustomer(customer);
		return ResponseEntity.ok(customer);
	}

}
