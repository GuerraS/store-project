package com.ed.store.entity;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name= "tbl_products" )
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8558525488310754198L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre del campo no debe ser vacio")
	private String name;
	private String description;
	@Positive(message = "Stock debe ser mayor a 0")
	private Double stock;
	private Double price;
	private String status;
	
	@Column(name="create_at")
	//Se toma date actual
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//Declara la relacion con la tabla categoria
	//Especifica en que columa hace la relacion con la llave foranea
	@jakarta.validation.constraints.NotNull(message = "La categoria no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Category category;
	
}
