package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** Finnish Trust Network (FTN) session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FtnStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = FtnStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = FtnStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class FtnStatus permits FtnStatus.Pending, FtnStatus.Completed, FtnStatus.Failed {
    /** Returned while the user has not yet acted. */
    public static final class Pending extends FtnStatus {
        public String id = "";
        public @Nullable String refId;
        public String url = "";
    }

    /** Returned when the FTN session has completed successfully. */
    public static final class Completed extends FtnStatus {
        public String id = "";
        public @Nullable String refId;
        public String ssn = "";
        public String name = "";
        public String givenName = "";
        public String surname = "";
        public @Nullable String phone;
        public @Nullable String email;
        public @Nullable String address;
        public @Nullable String birthDate;
        public @Nullable String pid;
        public @Nullable String bankId;
    }

    /** Returned when the FTN session has failed. */
    public static final class Failed extends FtnStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }
}
