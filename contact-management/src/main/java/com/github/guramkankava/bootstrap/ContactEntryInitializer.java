package com.github.guramkankava.bootstrap;

import java.util.List;

import org.springframework.boot.CommandLineRunner;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.repository.ContactRepository;

public class ContactEntryInitializer implements CommandLineRunner {

    private final ContactRepository contactRepository;

    public ContactEntryInitializer(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var contac1 = new Contact();
        contac1.setRowOwner("bill");
        contac1.setFirstname("John");
        contac1.setLastname("Doe");
        contac1.setEmail("john.doe@gmail.com");
        contac1.setAddress("Shota rustaveli ave N3");
        contac1.setMobilenumber("599599599");

        var contac2 = new Contact();
        contac2.setRowOwner("bill");
        contac2.setFirstname("Jane");
        contac2.setLastname("Doe");
        contac2.setEmail("jane.doe@gmail.com");
        contac2.setAddress("I.Chavchavadze 20");
        contac2.setMobilenumber("599500000");

        var contac3 = new Contact();
        contac3.setRowOwner("notbill");
        contac3.setFirstname("Bill");
        contac3.setLastname("Gates");
        contac3.setEmail("bill.gates@gmail.com");
        contac3.setAddress("1 Microsoft Way, Redmond, WA");
        contac3.setMobilenumber("177777777");

        contactRepository.saveAll(List.of(contac1, contac2, contac3));
    }

}
