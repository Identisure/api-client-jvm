package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** MitID session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = MitIdStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = MitIdStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = MitIdStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class MitIdStatus permits MitIdStatus.Pending, MitIdStatus.Completed, MitIdStatus.Failed {
    /** Returned while the user has not yet acted. */
    public static final class Pending extends MitIdStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String url;
        public @Nullable String bindingMessage;
    }

    /** Returned when the MitID session has completed successfully. */
    public static final class Completed extends MitIdStatus {
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
        public @Nullable SignResult signResult;
    }

    /** Returned when the MitID session has failed. */
    public static final class Failed extends MitIdStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }

    /** Signing result returned in a completed MitID sign session. */
    public static final class SignResult {
        public String checksum = "";
    }
}
