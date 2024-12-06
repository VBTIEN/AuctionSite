package com.example.AuctionSite.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.example.AuctionSite.dto.request.ContactRequest;
import com.example.AuctionSite.dto.response.ApiResponse;
import com.example.AuctionSite.dto.response.ContactResponse;
import com.example.AuctionSite.service.ContactService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContactController {
    ContactService contactService;

    @PostMapping("/create_contact")
    ApiResponse<ContactResponse> createContact(@Valid @RequestBody ContactRequest contactRequest) {
        return ApiResponse.<ContactResponse>builder()
                .result(contactService.createContact(contactRequest))
                .build();
    }

    @GetMapping("/get_all_contacts")
    ApiResponse<List<ContactResponse>> getAllContacts() {
        return ApiResponse.<List<ContactResponse>>builder()
                .result(contactService.getAllContacts())
                .build();
    }

    @GetMapping("/get_contact_by_id/{id}")
    ApiResponse<ContactResponse> getContactById(@PathVariable("id") Integer id) {
        return ApiResponse.<ContactResponse>builder()
                .result(contactService.getContactById(id))
                .build();
    }

    @PutMapping("/update_contact/{id}")
    ApiResponse<ContactResponse> updateContact(
            @PathVariable("id") Integer id, @RequestBody ContactRequest contactRequest) {
        return ApiResponse.<ContactResponse>builder()
                .result(contactService.updateContact(id, contactRequest))
                .build();
    }

    @DeleteMapping("/delete_contact/{id}")
    ApiResponse<String> deleteContact(@PathVariable("id") Integer id) {
        contactService.deleteContact(id);
        return ApiResponse.<String>builder().result("Contact deleted").build();
    }
}
