package se.idkollen.client.models;

/**
 * Request body for verifying a scanned BankID SE QR code.
 */
public class BankIdSeVerifyRequest {
    public String qrCode = "";

    public BankIdSeVerifyRequest setQrCode(String v) {
        qrCode = v;
        return this;
    }
}
