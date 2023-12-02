package com.github.guramkankava.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.repository.ContactRepository;

public class ContactPageableSearchService implements PageableSearchService<Contact> {

    private final ContactRepository contactRepository;

    private final AuthenticationService securityContextHolderAuthenticationService;

    public ContactPageableSearchService(ContactRepository contactRepository, AuthenticationService securityContextHolderAuthenticationService) {
        this.contactRepository = contactRepository;
        this.securityContextHolderAuthenticationService = securityContextHolderAuthenticationService;
    }

    @Override
    public Page<Contact> findPage(Pageable pageable, Contact contact) {
        var requestedPageSize = pageable.getPageSize();
        pageable = PageRequest.of(pageable.getPageNumber(), requestedPageSize > 11 || requestedPageSize < 0 ? 10 : requestedPageSize);

        ExampleMatcher matcher = ExampleMatcher.matching().
                withIgnoreNullValues().
                withIgnorePaths("id").
                withMatcher("firstname", match -> match.contains()).
                withMatcher("lastname", match -> match.contains()).
                withMatcher("mobilenumber", match -> match.contains()).
                withMatcher("email", match -> match.contains()).
                withMatcher("address", match -> match.contains()).
                withMatcher("rowOwner", match -> match.exact());

        contact.setRowOwner(securityContextHolderAuthenticationService.getAuthentication().getName());

        return contactRepository.findAll(Example.of(contact, matcher), pageable);
    }

}
