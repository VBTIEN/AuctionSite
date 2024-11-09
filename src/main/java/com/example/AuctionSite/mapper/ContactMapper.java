package com.example.AuctionSite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.AuctionSite.dto.request.ContactRequest;
import com.example.AuctionSite.dto.response.ContactResponse;
import com.example.AuctionSite.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "id", ignore = true)
    Contact toContact(ContactRequest contactRequest);

    ContactResponse toContactResponse(Contact contact);

    @Mapping(target = "id", ignore = true)
    void toUpdateContact(@MappingTarget Contact contact, ContactRequest contactRequest);
}
