package org.shopping.server.model.dao;

import org.shopping.server.model.Product;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductDAO extends ListCrudRepository<Product, Long> {

}
