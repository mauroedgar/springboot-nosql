/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.dao.impl;

import ag.fuel.dao.CustomerDAO;
import ag.fuel.pojo.Customer;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

/**
 * @author Mauro Sousa
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;
    private MongoCollection<Customer> customerCollection;

    public CustomerDAOImpl(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        customerCollection = client.getDatabase("pagaleve").getCollection("customers", Customer.class);
    }

    @Override
    public Customer save(Customer customer) {
        customer.setId(new ObjectId());
        customerCollection.insertOne(customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return customerCollection.findOneAndReplace(eq("_id", customer.getId()), customer, options);
    }

    @Override
    public List<Customer> findAll() {
        return customerCollection.find().into(new ArrayList<>());
    }

    @Override
    public Customer findOne(String id) {
        return customerCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public List<Customer> search(String query) {
        return customerCollection.find(new Document("$text", new Document("$search", query))).into(new ArrayList<>());
    }

    @Override
    public long count() {
        return customerCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return customerCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public List<Customer> saveAll(List<Customer> persons) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                persons.forEach(p -> p.setId(new ObjectId()));
                customerCollection.insertMany(clientSession, persons);
                return persons;
            }, txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> customerCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
