package com.identisure.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** Freja eID session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status", visible = true, defaultImpl = FrejaStatus.Unknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrejaStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = FrejaStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = FrejaStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class FrejaStatus permits FrejaStatus.Pending, FrejaStatus.Completed, FrejaStatus.Failed, FrejaStatus.Unknown {
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
        public @Nullable String authenticatedAt;
        public @Nullable String signedAt;
    }

    /** Returned when the Freja eID session has failed. */
    public static final class Failed extends FrejaStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }

    /** A status this client version does not recognize. */
    public static final class Unknown extends FrejaStatus {
        public final String status;

        @JsonCreator
        public Unknown(@JsonProperty(value = "status", required = true) String status) {
            this.status = Objects.requireNonNull(status, "Missing status field");
        }
    }
}
