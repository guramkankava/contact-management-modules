package com.github.guramkankava.service;

import static com.github.guramkankava.service.ContacServiceTestUtility.getContact;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.github.guramkankava.exception.ContactNotFoundException;
import com.github.guramkankava.repository.ContactRepository;

@ExtendWith(MockitoExtension.class)
class ContactRepositoryServiceTest {

    private static final String AUTH_USERNAME = "bill";

    private ContactService contactService;

    private ContactRepository contactRepository;

    private AuthenticationService authenticationService;

    private Authentication authentication;

    @BeforeEach
    void setupMocks() {
        this.contactRepository = Mockito.mock(ContactRepository.class);
        this.authenticationService = Mockito.mock(AuthenticationService.class);
        this.contactService = new ContactRepositoryService(contactRepository, authenticationService);
        this.authentication = Mockito.mock(Authentication.class);
    }

    @DisplayName("test update contact when contact not found")
    @Test
    void testUpdateNonExistentContact() {
        assertNotNull(contactService);
        final long contactId = 1L;
        var contact = getContact();
        when(authenticationService.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(AUTH_USERNAME);
        when(contactRepository.findByIdAndRowOwner(contactId, AUTH_USERNAME)).
        thenThrow(ContactNotFoundException.class);
        assertThrows(ContactNotFoundException.class, () -> {
            contactService.updateContact(1, contact);
        });
    }

}
