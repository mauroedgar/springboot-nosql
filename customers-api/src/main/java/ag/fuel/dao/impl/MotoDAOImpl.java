/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.dao.impl;

import ag.fuel.dao.MotoDAO;
import ag.fuel.pojo.Moto;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Mauro Sousa
 */
@Repository
public class MotoDAOImpl implements MotoDAO {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<Moto> motoCollection;

    public MotoDAOImpl(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        motoCollection = client.getDatabase("pagaleve").getCollection("motos", Moto.class);
    }


    @Override
    public List<Moto> findAll() {
        return motoCollection.find().into(new ArrayList<>());
    }

    @Override
    public Moto insert(String brand, String model, Integer cylinders) {
        Moto moto = new Moto();
        moto.setId(new ObjectId());
        moto.setBrand(brand);
        moto.setModel(model);
        moto.setCylinders(cylinders);
        motoCollection.insertOne(moto);
        return moto;
    }
}