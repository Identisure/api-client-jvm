package com.identisure.client.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/** Vipps MobilePay session status. Discriminated by the {@code status} JSON field. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "status", visible = true, defaultImpl = VippsStatus.Unknown.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = VippsStatus.Pending.class, name = "PENDING"),
    @JsonSubTypes.Type(value = VippsStatus.Completed.class, name = "COMPLETED"),
    @JsonSubTypes.Type(value = VippsStatus.Failed.class, name = "FAILED"),
})
public abstract sealed class VippsStatus
        permits VippsStatus.Pending, VippsStatus.Completed, VippsStatus.Failed, VippsStatus.Unknown {
    private VippsStatus() {}

    /** Returned while the user has not yet acted. */
    public static final class Pending extends VippsStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String url;

        public Pending() {}
    }

    /** Returned when the Vipps session has completed successfully. */
    public static final class Completed extends VippsStatus {
        public String id = "";
        public @Nullable String refId;
        public @Nullable String ssn;
        public String name = "";
        public String givenName = "";
        public String surname = "";
        public @Nullable String phone;
        public @Nullable String email;
        public @Nullable String address;
        public @Nullable String birthDate;
        public @Nullable String pid;
        public @Nullable String bankId;
        public @Nullable String authenticatedAt;

        public Completed() {}
    }

    /** Returned when the Vipps session has failed. */
    public static final class Failed extends VippsStatus {
        public String id = "";
        public @Nullable String refId;
        public ApiErrorCode error = ApiErrorCode.INTERNAL_ERROR;

        public Failed() {}
    }

    /** A status this client version does not recognize. */
    public static final class Unknown extends VippsStatus {
        public final String status;

        @JsonCreator
        public Unknown(@JsonProperty(value = "status", required = true) String status) {
            this.status = Objects.requireNonNull(status, "Missing status field");
        }
    }
}
