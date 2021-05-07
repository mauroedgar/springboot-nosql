/*
 * Copyright (c) 2021 Fuel.ag.
 * All rights reserved.
 * https://fuel.ag
 */
package ag.fuel;

import ag.fuel.pojo.Address;
import ag.fuel.pojo.Customer;
import ag.fuel.pojo.Moto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@Component
class TestCustomerMoto {

    Customer getMauro() {
        return new Customer().setFirstName("Mauro")
                .setLastName("Sousa")
                .setAddress(new Address().setCity("São Paulo")
                        .setCountry("Brazil")
                        .setNumber(292)
                        .setPostcode("05727-250")
                        .setStreet("Rua José Gonçalves"))
                .setAge(40)
                .setMotos(singletonList(new Moto().setBrand("Triumph").setModel("Speed Twin").setCylinders(1200)));
    }

    Customer getDelcio() {
        return new Customer().setFirstName("Delcio")
                .setLastName("Araújo")
                .setAddress(new Address().setCity("Alphaville")
                        .setCountry("Brazil")
                        .setNumber(2)
                        .setPostcode("00000-000")
                        .setStreet("Condomínio Burle Max"))
                .setAge(46)
                .setMotos(singletonList(new Moto().setBrand("Harley Davidson").setModel("Cruiser - Fat Bob").setCylinders(1868)));
    }

    List<Customer> getListCustomers() {
        return asList(getMauro(), getDelcio());
    }
}
