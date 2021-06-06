package com.tech.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.tech.model.Product;

public interface ProductService {

	Long saveProduct(Product product);
	void updateProduct(Product product);
	void deleteProduct(Long id);
	List<Product> getAllProduct();
	Optional<Product> getOneProduct(Long id);
	boolean isExist(Long id);
	ByteArrayInputStream customerToExcel(List<Product> product) throws IOException;
}

