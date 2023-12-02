package com.github.guramkankava.contoller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.guramkankava.converter.ContactConverter;
import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.request.ContactRequest;
import com.github.guramkankava.response.ContactResponse;
import com.github.guramkankava.service.ContactService;
import com.github.guramkankava.service.PageableSearchService;

@RequestMapping("/contacts")
@RestController
public class ContactController {

    private final ContactService contactService;

    private final ContactConverter contactConverter;

    private final PageableSearchService<Contact> contactPageableSearchService;

    public ContactController(ContactService contactService, ContactConverter contactConverter, PageableSearchService<Contact> contactPageableSearchService) {
        this.contactService = contactService;
        this.contactConverter = contactConverter;
        this.contactPageableSearchService = contactPageableSearchService;
    }

    @GetMapping
    public Page<ContactResponse> getPageOfContacts(Pageable pageable, ContactRequest contactRequest) {
        Contact contact = contactConverter.convertContactRequestToEntity(contactRequest);
        var page = contactPageableSearchService.findPage(pageable, contact);
        return page.map(contactConverter::converContactEntityToResponse);
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public ContactResponse addContact(@RequestBody ContactRequest contactRequest, Principal principal) {
        Contact contact = contactConverter.convertContactRequestToEntity(contactRequest);
        contact = contactService.addContact(contact);
        ContactResponse contactResponse = contactConverter.converContactEntityToResponse(contact);
        return contactResponse;
    }

    @PutMapping("/{id}")
    public ContactResponse updateContact(@PathVariable long id, @RequestBody ContactRequest contactRequest) {
        Contact contact = contactConverter.convertContactRequestToEntity(contactRequest);
        contact = contactService.updateContact(id, contact);
        ContactResponse contactResponse = contactConverter.converContactEntityToResponse(contact);
        return contactResponse;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable long id) {
        contactService.deleteContact(id);
    }
}
