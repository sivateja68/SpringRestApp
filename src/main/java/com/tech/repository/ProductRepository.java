package com.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
