package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** Freja eID session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrejaStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = FrejaStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = FrejaStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class FrejaStatus permits FrejaStatus.Pending, FrejaStatus.Completed, FrejaStatus.Failed {
    /** Returned while the user has not yet acted in the Freja app. */
    public static final class Pending extends FrejaStatus {
        public String id = "";
        public @Nullable String refId;
        public String autoStartToken = "";
        public String qrData = "";
    }

    /** Returned when the Freja eID session has completed successfully. */
    public static final class Completed extends FrejaStatus {
        public String id = "";
        public @Nullable String refId;
        public String ssn = "";
        public String country = "";
        public String name = "";
        public String givenName = "";
        public String surname = "";
        public @Nullable String address;
        public @Nullable String companySignatoryText;
    }

    /** Returned when the Freja eID session has failed. */
    public static final class Failed extends FrejaStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }
}
