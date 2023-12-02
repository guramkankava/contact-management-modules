package com.github.guramkankava.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.request.ContactRequest;
import com.github.guramkankava.response.ContactResponse;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toContactEntity(ContactRequest contactRequest);

    ContactResponse toContactResponse(Contact contact);

}
