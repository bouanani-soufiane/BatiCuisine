package org.example.dtos.requests;

public record ClientRequest(
    String name,
    String address,
    String phone,
    Boolean isProfessional
) {
}