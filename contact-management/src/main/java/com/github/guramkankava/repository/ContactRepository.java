package com.github.guramkankava.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.guramkankava.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByIdAndRowOwner(long id, String owner);

}
