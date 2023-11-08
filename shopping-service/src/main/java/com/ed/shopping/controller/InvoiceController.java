package com.ed.shopping.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ed.shopping.entity.Invoice;
import com.ed.shopping.service.InvoiceService;
import com.ed.shopping.utils.ErrorMessage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;

	// -------------------Retrieve All
	// Invoices--------------------------------------------
	@GetMapping
	public ResponseEntity<List<Invoice>> listAllInvoices() {
		List<Invoice> invoices = invoiceService.findAllInvoices();
		if (invoices.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(invoices);
	}

	// -------------------Retrieve Single
	// Invoice------------------------------------------
	@GetMapping(value = "/{id}")
	public ResponseEntity<Invoice> getInvoice(@PathVariable("id") long id) {
		log.info("Fetching Invoice with id {}", id);
		Invoice invoice = invoiceService.getInvoice(id);
		if (null == invoice) {
			log.error("Invoice with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(invoice);
	}

	// -------------------Create a
	// Invoice-------------------------------------------
	@PostMapping
	public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) {
		log.info("Creating Invoice : {}", invoice);

		if (result.hasErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			String error = errorMessage.formatMessaage(result);
			log.error("Json with errors", error);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
		}
		Invoice invoiceDB = invoiceService.createInvoice(invoice);

		return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDB);
	}

	// ------------------- Update a Invoice
	// ------------------------------------------------
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateInvoice(@PathVariable("id") long id, @RequestBody Invoice invoice,
			BindingResult result) {
		log.info("Updating Invoice with id {}", id);

		if (result.hasErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			String error = errorMessage.formatMessaage(result);
			log.error("Json with errors", error);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, error);
		}

		invoice.setId(id);
		Invoice currentInvoice = invoiceService.updateInvoice(invoice);

		if (currentInvoice == null) {
			log.error("Unable to update. Invoice with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(currentInvoice);
	}

	// ------------------- Delete a Invoice-----------------------------------------
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") long id) {
		log.info("Fetching & Deleting Invoice with id {}", id);

		Invoice invoice = invoiceService.getInvoice(id);
		if (invoice == null) {
			log.error("Unable to delete. Invoice with id {} not found.", id);
			return ResponseEntity.notFound().build();
		}
		invoice = invoiceService.deleteInvoice(invoice);
		return ResponseEntity.ok(invoice);
	}

}