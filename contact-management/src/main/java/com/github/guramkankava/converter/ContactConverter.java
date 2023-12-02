package com.github.guramkankava.converter;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.request.ContactRequest;
import com.github.guramkankava.response.ContactResponse;

public interface ContactConverter {

    Contact convertContactRequestToEntity(ContactRequest contactRequest);

    ContactResponse converContactEntityToResponse(Contact contact);

}
