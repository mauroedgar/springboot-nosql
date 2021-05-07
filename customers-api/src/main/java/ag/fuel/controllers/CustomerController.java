/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.controllers;

import ag.fuel.dao.CustomerDAO;
import ag.fuel.pojo.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author Mauro Sousa
 */
@RestController
@RequestMapping("/api")
public class CustomerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerDAO customerDAO;

    public CustomerController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    //Insert
    @PostMapping("customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer postCustomer(@RequestBody Customer customer) {
        return customerDAO.save(customer);
    }

    //Update
    @PutMapping("customer")
    public Customer putCustomer(@RequestBody Customer customer) {
        return customerDAO.update(customer);
    }

    //List All
    @GetMapping("customers")
    public List<Customer> getCustomers() {
        return customerDAO.findAll();
    }

    //List One
    @GetMapping("customer/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        Customer customer = customerDAO.findOne(id);
        if (customer == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(customer);
    }

    //Count
    @GetMapping("customers/count")
    public Long getCount() {
        return customerDAO.count();
    }

    //Delete One
    @DeleteMapping("customer/{id}")
    public Long deleteCustomer(@PathVariable String id) {
        return customerDAO.delete(id);
    }

    //Search
    @GetMapping("customers/{query}")
    public List<Customer> searchAll(@PathVariable String query) {
        return customerDAO.search(query);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Customer> postPersons(@RequestBody List<Customer> customers) {
        return customerDAO.saveAll(customers);
    }
}
