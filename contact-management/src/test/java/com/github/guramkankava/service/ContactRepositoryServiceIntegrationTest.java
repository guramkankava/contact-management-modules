package com.github.guramkankava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.util.StringUtils;

import com.github.guramkankava.entity.Contact;

@SpringBootTest
class ContactRepositoryServiceIntegrationTest {

    private static final String AUTH_USERNAME = "bill";

    @Autowired
    private ContactService contactRepositoryService;

    @WithMockUser(username = AUTH_USERNAME)
    @Test
    void testAddContact() {
        var contact = getContact();
        assertFalse(StringUtils.hasText(contact.getRowOwner()));
        assertEquals(0L, contact.getId());
        contact = contactRepositoryService.addContact(contact);
        assertEquals(AUTH_USERNAME, contact.getRowOwner());
        assertTrue((contact.getId() > 0L));
    }

    private Contact getContact() {
        var contac = new Contact();
        contac.setFirstname("James");
        contac.setLastname("Gosling");
        contac.setEmail("james.gosling@gmail.com");
        contac.setAddress("Palm tree N3");
        contac.setMobilenumber("17777778");
        return contac;
    }
}
