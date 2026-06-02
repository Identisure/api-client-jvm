package se.idkollen.client.models;

import org.jspecify.annotations.Nullable;

/** Response from the BankID SE QR code verification endpoint. */
public class BankIdSeVerifyResponse {
    public String ssn = "";
    public String name = "";
    public String givenName = "";
    public String surname = "";
    public @Nullable Integer age;
    public @Nullable String verifiedAt;
}
