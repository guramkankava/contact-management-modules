package com.github.guramkankava.entity;

import java.util.Objects;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(schema = "contactmanagement", name = "contacts")
@Entity
public class Contact {

    @GenericGenerator(name = "contact_id_gen")
    @GeneratedValue(generator = "contact_id_gen")
    @Id
    private long id;

    private String firstname;

    private String lastname;

    private String mobilenumber;

    private String email;

    private String address;

    @Column(nullable = false)
    private String rowOwner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRowOwner() {
        return rowOwner;
    }

    public void setRowOwner(String rowOwner) {
        this.rowOwner = rowOwner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, email, firstname, id, lastname, mobilenumber, rowOwner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        return Objects.equals(address, other.address) && Objects.equals(email, other.email)
                && Objects.equals(firstname, other.firstname) && id == other.id
                && Objects.equals(lastname, other.lastname) && Objects.equals(mobilenumber, other.mobilenumber)
                && Objects.equals(rowOwner, other.rowOwner);
    }

}
