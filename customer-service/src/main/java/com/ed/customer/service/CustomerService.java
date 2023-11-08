package com.ed.customer.service;

import java.util.List;

import com.ed.customer.entity.Customer;
import com.ed.customer.entity.Region;

public interface CustomerService {
	public List<Customer> listAllCustomers();

	public List<Customer> findCustomersByRegion(Region region);

	public Customer createCustomer(Customer customer);

	public Customer updateCustomer(Customer customer);

	public Customer deleteCustomer(Customer customer);

	public Customer getCustomer(Long id);
}
