/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * @author Mauro Sousa
 */
@JsonInclude(Include.NON_NULL)
public class Customer {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String firstName;
    private String lastName;
    private int age;
    private Address address;
    private List<Moto> motos;
    private Date createdAt = new Date();

    public ObjectId getId() {
        return id;
    }

    public Customer setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Customer setAge(int age) {
        this.age = age;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Customer setAddress(Address address) {
        this.address = address;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Customer setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<Moto> getMotos() {
        return motos;
    }

    public Customer setMotos(List<Moto> motos) {
        this.motos = motos;
        return this;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", age=" + age + ", address=" + address + ", motos=" + motos + ", createdAt=" + createdAt + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Customer customer = (Customer) o;
        return age == customer.age && Objects.equals(id, customer.id) && Objects.equals(firstName,
                customer.firstName) && Objects.equals(lastName,
                customer.lastName) && Objects
                .equals(address, customer.address) && Objects.equals(motos, customer.motos)
                && Objects.equals(createdAt, customer.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, age, address, motos, createdAt);
    }

}