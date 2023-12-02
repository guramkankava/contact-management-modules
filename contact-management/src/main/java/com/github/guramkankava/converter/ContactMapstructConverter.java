package com.github.guramkankava.converter;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.mapper.ContactMapper;
import com.github.guramkankava.request.ContactRequest;
import com.github.guramkankava.response.ContactResponse;

public class ContactMapstructConverter implements ContactConverter {

    private final ContactMapper contactMapper;

    public ContactMapstructConverter(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    @Override
    public Contact convertContactRequestToEntity(ContactRequest contactRequest) {
        return contactMapper.toContactEntity(contactRequest);
    }

    @Override
    public ContactResponse converContactEntityToResponse(Contact contact) {
        return contactMapper.toContactResponse(contact);
    }

}
