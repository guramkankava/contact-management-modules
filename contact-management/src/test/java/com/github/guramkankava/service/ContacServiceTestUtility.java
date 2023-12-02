package com.github.guramkankava.service;

import com.github.guramkankava.entity.Contact;

public class ContacServiceTestUtility {

    private ContacServiceTestUtility() {}

    public static Contact getContact() {
        var contac = new Contact();
        contac.setFirstname("James");
        contac.setLastname("Gosling");
        contac.setEmail("james.gosling@gmail.com");
        contac.setAddress("Palm tree N3");
        contac.setMobilenumber("17777778");
        return contac;
    }
}
