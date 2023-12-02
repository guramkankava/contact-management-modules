package com.github.guramkankava.service;

import com.github.guramkankava.entity.Contact;

public interface ContactService {

    Contact addContact(Contact contact);

    Contact updateContact(long id, Contact contact);

    void deleteContact(long id);

}
