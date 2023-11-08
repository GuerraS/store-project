package com.ed.shopping.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ed.shopping.entity.InvoiceItem;

public interface InvoiceItemsRepository extends JpaRepository<InvoiceItem,Long>{

}
