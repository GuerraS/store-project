package com.ed.shopping.service;

import java.util.List;

import com.ed.shopping.entity.Invoice;

public interface InvoiceService {
	  public List<Invoice> findAllInvoices();

	    public Invoice createInvoice(Invoice invoice);
	    public Invoice updateInvoice(Invoice invoice);
	    public Invoice deleteInvoice(Invoice invoice);

	    public Invoice getInvoice(Long id);
}
