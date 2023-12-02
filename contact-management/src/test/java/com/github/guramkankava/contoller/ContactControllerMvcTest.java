package com.github.guramkankava.contoller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guramkankava.response.ContactResponse;

@AutoConfigureMockMvc
@SpringBootTest
class ContactControllerMvcTest {

    private static final String UTF_8 = "utf-8";
    private static final String CONTACTS = "/contacts";
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetContactsWithoutAuthentication() throws Exception {
        mockMvc.perform(get(CONTACTS)).andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "bill")
    @Test
    void testGetContactsWithAuthentication() throws Exception {
        mockMvc.perform(get(CONTACTS)).andExpect(status().is2xxSuccessful());
    }

    @Test
    void testAddContactWithoutAuthentication() throws Exception {
        var body = readFromFileToString("contact.json");
        mockMvc.perform(post(CONTACTS).
                with(csrf()).
                contentType(APPLICATION_JSON).
                characterEncoding(UTF_8).
                content(body)).
        andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "bill")
    @Test
    void testAddContactWithAuthentication() throws Exception {
        var body = readFromFileToString("contact.json");
        mockMvc.perform(post(CONTACTS).
                with(csrf()).
                contentType(APPLICATION_JSON).
                characterEncoding(UTF_8).
                content(body)).
        andExpect(status().isCreated());
    }

    @WithMockUser(username = "bill")
    @Test
    void testUpdateContactWithAuthentication() throws Exception {
        var body = readFromFileToString("contact.json");
        final String response = mockMvc.perform(post(CONTACTS).
                with(csrf()).
                contentType(APPLICATION_JSON).
                characterEncoding(UTF_8).
                content(body)).
        andReturn().
        getResponse().
        getContentAsString();
        System.err.println(response);
        var contactResponse = jsonStringTo(response, ContactResponse.class);

        mockMvc.perform(put(CONTACTS + "/" + contactResponse.getId()).
                with(csrf()).
                contentType(APPLICATION_JSON).
                characterEncoding(UTF_8).
                content(body)).
        andExpect(status().is2xxSuccessful());
    }

    private <T> T jsonStringTo(String json, Class<T> t) throws JsonProcessingException, JsonMappingException {
        return new ObjectMapper().readValue(json, t);
    }

    private static String readFromFileToString(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }
}
