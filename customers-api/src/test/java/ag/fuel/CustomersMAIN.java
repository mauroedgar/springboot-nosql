/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel;

import ag.fuel.dao.CustomerDAO;
import ag.fuel.pojo.Customer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomersMAIN {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private TestCustomerMoto testCustomerMoto;
    private String URL;

    @Autowired
    CustomersMAIN(MongoClient mongoClient) {
        createCustomerCollectionIfNotPresent(mongoClient);
    }

    @PostConstruct
    void setUp() {
        URL = "http://localhost:" + port + "/api";
    }

    @AfterEach
    void tearDown() {
        customerDAO.deleteAll();
    }

    @DisplayName("POST /customer with 1 customer")
    @Test
    void postCustomer() {
        ResponseEntity<Customer> result = rest.postForEntity(URL + "/customer", testCustomerMoto.getMauro(), Customer.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Customer customerResult = result.getBody();
        assertThat(customerResult.getId()).isNotNull();
        assertThat(customerResult).usingRecursiveComparison().ignoringFields("id", "createdAt").isEqualTo(testCustomerMoto.getMauro());
    }

    @DisplayName("POST /customers with 2 customer")
    @Test
    void postCustomers() {
        HttpEntity<List<Customer>> body = new HttpEntity<>(testCustomerMoto.getListCustomers());
        ResponseEntity<List<Customer>> response = rest.exchange(URL + "/customers", HttpMethod.
                POST, body, new ParameterizedTypeReference<List<Customer>>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingElementComparatorIgnoringFields("id", "createdAt")
                .containsExactlyInAnyOrderElementsOf(testCustomerMoto.getListCustomers());
    }

    @DisplayName("GET /customers with 2 customers")
    @Test
    void getCustomers() {
        List<Customer> customersInserted = customerDAO.saveAll(testCustomerMoto.getListCustomers());
        ResponseEntity<List<Customer>> result = rest.exchange(URL + "/customers", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Customer>>() {
                });
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).containsExactlyInAnyOrderElementsOf(customersInserted);
    }

    @DisplayName("GET /customer/{id}")
    @Test
    void getCustomerById() {
        Customer customerInserted = customerDAO.save(testCustomerMoto.getDelcio());
        ObjectId idInserted = customerInserted.getId();
        ResponseEntity<Customer> result = rest.getForEntity(URL + "/customer/" + idInserted, Customer.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(customerInserted);
    }

    @DisplayName("GET /customers/count")
    @Test
    void getCount() {
        customerDAO.saveAll(testCustomerMoto.getListCustomers());
        ResponseEntity<Long> result = rest.getForEntity(URL + "/customers/count", Long.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
    }

    @DisplayName("DELETE /customer/{id}")
    @Test
    void deleteCustomerById() {
        Customer customerInserted = customerDAO.save(testCustomerMoto.getMauro());
        ObjectId idInserted = customerInserted.getId();
        ResponseEntity<Long> result = rest.exchange(URL + "/customer/" + idInserted.toString(), HttpMethod.DELETE, null,
                new ParameterizedTypeReference<Long>() {
                });
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(1L);
        assertThat(customerDAO.count()).isEqualTo(0L);
    }

    @DisplayName("PUT /customer")
    @Test
    void putCustomer() {
        Customer customerInserted = customerDAO.save(testCustomerMoto.getMauro());
        customerInserted.setAge(40);
        HttpEntity<Customer> body = new HttpEntity<>(customerInserted);
        ResponseEntity<Customer> result = rest.exchange(URL + "/customer", HttpMethod.PUT, body,
                new ParameterizedTypeReference<Customer>() {
                });
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(customerDAO.findOne(customerInserted.getId().toString()));
        assertThat(result.getBody().getAge()).isEqualTo(40);
        assertThat(customerDAO.count()).isEqualTo(1L);
    }
    

    private void createCustomerCollectionIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase db = mongoClient.getDatabase("test");
        if (!db.listCollectionNames().into(new ArrayList<>()).contains("customers"))
            db.createCollection("customers");
    }
}
