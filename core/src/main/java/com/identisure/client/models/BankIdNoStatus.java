package com.identisure.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import java.util.List;

/** BankID NO session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status", visible = true, defaultImpl = BankIdNoStatus.Unknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BankIdNoStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = BankIdNoStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = BankIdNoStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class BankIdNoStatus permits BankIdNoStatus.Pending, BankIdNoStatus.Completed, BankIdNoStatus.Failed, BankIdNoStatus.Unknown {
    /** Returned while the user has not yet acted. */
    public static final class Pending extends BankIdNoStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String url;
        public @Nullable String bindingMessage;
    }

    /** Returned when the BankID NO session has completed successfully. */
    public static final class Completed extends BankIdNoStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String ssn;
        public String name = "";
        public String givenName = "";
        public String surname = "";
        public @Nullable String phone;
        public @Nullable String email;
        public @Nullable String address;
        public @Nullable String birthDate;
        public @Nullable String pid;
        public @Nullable String bankId;
        public @Nullable SignResult signResult;
        public @Nullable List<SignedDocument> signedDocuments;
        public @Nullable String authenticatedAt;
        public @Nullable String signedAt;
    }

    /** Returned when the BankID NO session has failed. */
    public static final class Failed extends BankIdNoStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }

    /** Signing result returned in a completed BankID NO sign session. */
    public static final class SignResult {
        public String endUser = "";
        public String merchant = "";
        public String hash = "";
    }

    /** A signed document reference returned in a completed BankID NO sign session. */
    public static final class SignedDocument {
        public String id = "";
        public String hash = "";
    }

    /** A status this client version does not recognize. */
    public static final class Unknown extends BankIdNoStatus {
        public final String status;

        @JsonCreator
        public Unknown(@JsonProperty(value = "status", required = true) String status) {
            this.status = Objects.requireNonNull(status, "Missing status field");
        }
    }
}
