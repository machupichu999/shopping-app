package org.shopping.server.model.dao;

import org.shopping.server.model.LocalUser;
import org.shopping.server.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);
}
