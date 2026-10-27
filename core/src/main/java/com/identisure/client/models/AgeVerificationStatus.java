package com.identisure.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** Age verification session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status", visible = true, defaultImpl = AgeVerificationStatus.Unknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AgeVerificationStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = AgeVerificationStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = AgeVerificationStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class AgeVerificationStatus permits AgeVerificationStatus.Pending, AgeVerificationStatus.Completed, AgeVerificationStatus.Failed, AgeVerificationStatus.Unknown {
    /** Returned while the user has not yet completed age verification. */
    public static final class Pending extends AgeVerificationStatus {
        public String id = "";
        public @Nullable String url;
        public @Nullable Integer minAge;
        public @Nullable Integer maxAge;
    }

    /** Returned when the age verification session has completed. */
    public static final class Completed extends AgeVerificationStatus {
        public String id = "";
        public boolean ageVerified;
    }

    /** Returned when the age verification session has failed. */
    public static final class Failed extends AgeVerificationStatus {
        public String id = "";
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }

    /** A status this client version does not recognize. */
    public static final class Unknown extends AgeVerificationStatus {
        public final String status;

        @JsonCreator
        public Unknown(@JsonProperty(value = "status", required = true) String status) {
            this.status = Objects.requireNonNull(status, "Missing status field");
        }
    }
}
