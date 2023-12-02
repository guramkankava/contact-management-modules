package com.github.guramkankava.service;

import org.springframework.transaction.annotation.Transactional;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.exception.ContactNotFoundException;
import com.github.guramkankava.repository.ContactRepository;

@Transactional
public class ContactRepositoryService implements ContactService {

    private final ContactRepository contactRepository;

    private final AuthenticationService securityContextHolderAuthenticationService;

    public ContactRepositoryService(ContactRepository contactRepository, AuthenticationService securityContextHolderAuthenticationService) {
        this.contactRepository = contactRepository;
        this.securityContextHolderAuthenticationService = securityContextHolderAuthenticationService;
    }

    @Override
    public Contact addContact(Contact contact) {
        contact.setRowOwner(securityContextHolderAuthenticationService.getAuthentication().getName());
        return contactRepository.save(contact);
    }


    @Override
    public Contact updateContact(long id, Contact contact) {
        findOwnContact(id);
        contact.setId(id);
        contact.setRowOwner(securityContextHolderAuthenticationService.getAuthentication().getName());
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(long id) {
        findOwnContact(id);
        contactRepository.deleteById(id);
    }

    private Contact findOwnContact(long id) {
        return contactRepository.findByIdAndRowOwner(id, securityContextHolderAuthenticationService.getAuthentication().getName()).
            orElseThrow(() -> new ContactNotFoundException("contact not found, id " + id));
    }
}
