package com.ed.customer.repository;

import java.util.List;

import com.ed.customer.entity.Customer;
import com.ed.customer.entity.Region;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {
	   public Customer findByNumberID(String numberID);
	   public Customer findByEmail(String email);

       public List<Customer> findByLastName(String lastName);
       public List<Customer> findByRegion(Region region);
}
