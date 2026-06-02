package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** Age verification session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AgeVerificationStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = AgeVerificationStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = AgeVerificationStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class AgeVerificationStatus permits AgeVerificationStatus.Pending, AgeVerificationStatus.Completed, AgeVerificationStatus.Failed {
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
}
