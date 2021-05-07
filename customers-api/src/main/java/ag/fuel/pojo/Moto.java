/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.pojo;

import org.bson.types.ObjectId;

import java.util.Objects;
/**
 * @author Mauro Sousa
 */
public class Moto {

    private ObjectId id;
    private String brand;
    private String model;
    private int cylinders;

    public Moto() {
    }

    public ObjectId getId() {
        return id;
    }

    public Moto setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Moto setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public Moto setModel(String model) {
        this.model = model;
        return this;
    }

    public int getCylinders() {
        return cylinders;
    }

    public Moto setCylinders(int cylinders) {
        this.cylinders = cylinders;
        return this;
    }

    @Override
    public String toString() {
        return "Moto{" + "id=" + id + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", cylinders=" + cylinders + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Moto moto = (Moto) o;
        return Objects.equals(id, moto.id)
                && Objects.equals(brand, moto.brand)
                && Objects.equals(model, moto.model)
                && Objects.equals(cylinders, moto.cylinders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, cylinders);
    }
}

