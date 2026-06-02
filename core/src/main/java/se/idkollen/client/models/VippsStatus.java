package se.idkollen.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jspecify.annotations.Nullable;

/** Vipps MobilePay session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status")
@JsonSubTypes({
    @JsonSubTypes.Type(value = VippsStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = VippsStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = VippsStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class VippsStatus permits VippsStatus.Pending, VippsStatus.Completed, VippsStatus.Failed {
    /** Returned while the user has not yet acted. */
    public static final class Pending extends VippsStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String url;
    }

    /** Returned when the Vipps session has completed successfully. */
    public static final class Completed extends VippsStatus {
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

    /** Returned when the Vipps session has failed. */
    public static final class Failed extends VippsStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;
    }
}
