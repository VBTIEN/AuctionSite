package com.example.AuctionSite.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.AuctionSite.dto.request.ContactRequest;
import com.example.AuctionSite.dto.response.ContactResponse;
import com.example.AuctionSite.entity.Contact;
import com.example.AuctionSite.mapper.ContactMapper;
import com.example.AuctionSite.repository.ContactRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactService {
    ContactRepository contactRepository;
    ContactMapper contactMapper;

    @PreAuthorize("hasAuthority('CREATE_CONTACT')")
    public ContactResponse createContact(ContactRequest contactRequest) {
        Contact contact = contactMapper.toContact(contactRequest);
        return contactMapper.toContactResponse(contactRepository.save(contact));
    }

    @PreAuthorize("hasAuthority('GET_ALL_CONTACTS')")
    public List<ContactResponse> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toContactResponse)
                .toList();
    }

    @PreAuthorize("hasAuthority('GET_CONTACT_BY_ID')")
    public ContactResponse getContactById(Integer id) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        return contactMapper.toContactResponse(contact);
    }

    @PreAuthorize("hasAuthority('UPDATE_CONTACT')")
    public ContactResponse updateContact(Integer id, ContactRequest contactRequest) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        contactMapper.toUpdateContact(contact, contactRequest);
        return contactMapper.toContactResponse(contactRepository.save(contact));
    }

    @PreAuthorize("hasAuthority('DELETE_CONTACT')")
    public void deleteContact(Integer id) {
        contactRepository.deleteById(id);
    }
}
