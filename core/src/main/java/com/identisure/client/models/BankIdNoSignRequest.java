package com.identisure.client.models;

import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Request body for starting a BankID NO signing session.
 */
public class BankIdNoSignRequest {
    public @Nullable String redirectUrl;
    /**
     * Text to sign (max 118 chars). Mutually exclusive with {@code documents}.
     */
    public @Nullable String text;
    /**
     * Document IDs to sign (from {@code /v3/document}). Mutually exclusive with {@code text}.
     */
    public @Nullable List<String> documents;
    public @Nullable String refId;

    public BankIdNoSignRequest setRedirectUrl(@Nullable String v) {
        redirectUrl = v;
        return this;
    }

    public BankIdNoSignRequest setText(@Nullable String v) {
        text = v;
        return this;
    }

    public BankIdNoSignRequest setDocuments(@Nullable List<String> v) {
        documents = v;
        return this;
    }

    public BankIdNoSignRequest setRefId(@Nullable String v) {
        refId = v;
        return this;
    }
}
