package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** BankID SE session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BankIdSeStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = BankIdSeStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = BankIdSeStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class BankIdSeStatus permits BankIdSeStatus.Pending, BankIdSeStatus.Completed, BankIdSeStatus.Failed {
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
        public @Nullable String address;
        public @Nullable String companySignatoryText;
    }

    /** Returned when the BankID SE session has failed. */
    public static final class Failed extends BankIdSeStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }
}
