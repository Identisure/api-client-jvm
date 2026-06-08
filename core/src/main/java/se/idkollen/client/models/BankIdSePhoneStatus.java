package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** BankID SE session status for phone auth and sign flows. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BankIdSePhoneStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = BankIdSePhoneStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = BankIdSePhoneStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class BankIdSePhoneStatus permits BankIdSePhoneStatus.Pending, BankIdSePhoneStatus.Completed, BankIdSePhoneStatus.Failed {
    /** Returned while the user has not yet acted in a phone auth or sign flow. */
    public static final class Pending extends BankIdSePhoneStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String hintCode;
    }

    /** Returned when the BankID SE phone session has completed successfully. */
    public static final class Completed extends BankIdSePhoneStatus {
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

    /** Returned when the BankID SE phone session has failed. */
    public static final class Failed extends BankIdSePhoneStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }
}
