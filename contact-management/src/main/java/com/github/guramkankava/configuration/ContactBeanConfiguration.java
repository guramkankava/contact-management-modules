package com.github.guramkankava.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.guramkankava.bootstrap.ContactEntryInitializer;
import com.github.guramkankava.converter.ContactConverter;
import com.github.guramkankava.converter.ContactMapstructConverter;
import com.github.guramkankava.entity.Contact;
import com.github.guramkankava.mapper.ContactMapper;
import com.github.guramkankava.repository.ContactRepository;
import com.github.guramkankava.service.AuthenticationService;
import com.github.guramkankava.service.ContactRepositoryService;
import com.github.guramkankava.service.ContactPageableSearchService;
import com.github.guramkankava.service.ContactService;
import com.github.guramkankava.service.PageableSearchService;

@Configuration
public class ContactBeanConfiguration {

    @Bean
    ContactConverter contactConverter (ContactMapper contactMapper) {
        return new ContactMapstructConverter(contactMapper);
    }

    @Bean
    ContactService contactService(ContactRepository contactRepository, AuthenticationService securityContextHolderAuthenticationService) {
        return new ContactRepositoryService(contactRepository, securityContextHolderAuthenticationService);
    }

    @Bean
    PageableSearchService<Contact> contactPageableSearchService(ContactRepository contactRepository, AuthenticationService securityContextHolderAuthenticationService) {
        return new ContactPageableSearchService(contactRepository, securityContextHolderAuthenticationService);
    }

    @Bean
    ContactEntryInitializer contacEntryInitializer(ContactRepository contactRepository) {
        return new ContactEntryInitializer(contactRepository);
    }
}
