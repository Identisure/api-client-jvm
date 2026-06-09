package se.idkollen.client;

import okhttp3.OkHttpClient;
import se.idkollen.client.endpoints.*;
import se.idkollen.client.internal.Transport;

/**
 * Authenticated HTTP client for the IDkollen REST API.
 *
 * <p>Obtain an instance via {@link IdkollenClientBuilder}. All network calls return
 * {@link java.util.concurrent.CompletableFuture} and are non-blocking.
 */
public final class IdkollenClient {
    private final Transport transport;

    IdkollenClient(String baseUrl, String clientId, String clientSecret, String userAgent, OkHttpClient http) {
        this.transport = new Transport(baseUrl, clientId, clientSecret, userAgent, http);
    }

    /** Return the BankID SE endpoint accessor. */
    public BankIdSeEndpoint bankIdSe() {
        return new BankIdSeEndpoint(transport);
    }

    /** Return the BankID NO endpoint accessor. */
    public BankIdNoEndpoint bankIdNo() {
        return new BankIdNoEndpoint(transport);
    }

    /** Return the Freja eID endpoint accessor. */
    public FrejaEndpoint freja() {
        return new FrejaEndpoint(transport);
    }

    /** Return the MitID endpoint accessor. */
    public MitIdEndpoint mitId() {
        return new MitIdEndpoint(transport);
    }

    /** Return the Finnish Trust Network (FTN) endpoint accessor. */
    public FtnEndpoint ftn() {
        return new FtnEndpoint(transport);
    }

    /** Return the Vipps MobilePay endpoint accessor. */
    public VippsEndpoint vipps() {
        return new VippsEndpoint(transport);
    }

    /** Return the document upload/download endpoint accessor. */
    public DocumentEndpoint document() {
        return new DocumentEndpoint(transport);
    }
}
