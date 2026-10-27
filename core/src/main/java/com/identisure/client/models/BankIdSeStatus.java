package com.identisure.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** BankID SE session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status", visible = true, defaultImpl = BankIdSeStatus.Unknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BankIdSeStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = BankIdSeStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = BankIdSeStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class BankIdSeStatus permits BankIdSeStatus.Pending, BankIdSeStatus.Completed, BankIdSeStatus.Failed, BankIdSeStatus.Unknown {
    /** Returned while the user has not yet acted in the BankID app. */
    public static final class Pending extends BankIdSeStatus {
        public String id = "";
        public @Nullable String refId;
        public String autoStartToken = "";
        public String qrStartToken = "";
        public String qrStartSecret = "";
        public @Nullable String hintCode;
    }

    /** Returned when the BankID SE session has completed successfully. */
    public static final class Completed extends BankIdSeStatus {
        public String id = "";
        public @Nullable String refId;
        public String ssn = "";
        public String name = "";
        public String givenName = "";
        public String surname = "";
        public String certStartDate = "";
        public String ocspResponse = "";
        public @Nullable String risk;
        public @Nullable Device device;
        public @Nullable StepUp stepUp;
        public @Nullable String address;
        public @Nullable String companySignatoryText;
        public @Nullable String authenticatedAt;
        public @Nullable String signedAt;
    }

    /** The user's device, as seen by BankID. */
    public static final class Device {
        public @Nullable String ipAddress;
        public @Nullable String uhi;
    }

    /** Additional verifications performed before the order completed. */
    public static final class StepUp {
        public @Nullable Boolean mrtd;
        public @Nullable Boolean face;
    }

    /** Returned when the BankID SE session has failed. */
    public static final class Failed extends BankIdSeStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }

    /** A status this client version does not recognize. */
    public static final class Unknown extends BankIdSeStatus {
        public final String status;

        @JsonCreator
        public Unknown(@JsonProperty(value = "status", required = true) String status) {
            this.status = Objects.requireNonNull(status, "Missing status field");
        }
    }
}
