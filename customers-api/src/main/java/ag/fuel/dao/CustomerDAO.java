/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.dao;

import ag.fuel.pojo.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author Mauro Sousa
 */
@Repository
public interface CustomerDAO {

    Customer save(Customer customer);

    Customer update(Customer customer);

    List<Customer> findAll();

    Customer findOne(String id);

    List<Customer> search(String query);

    long count();

    long delete(String id);

    List<Customer> saveAll(List<Customer> customers);

    long deleteAll();

}
