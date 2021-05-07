/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.dao;

import ag.fuel.pojo.Moto;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author Mauro Sousa
 */
@Repository
public interface MotoDAO {

    List<Moto> findAll();

    Moto insert(String brand, String model, Integer cylinders);
}
