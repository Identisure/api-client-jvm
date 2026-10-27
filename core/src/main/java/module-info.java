module com.identisure.client {
    requires okhttp3;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires static org.jspecify;

    exports com.identisure.client;
    exports com.identisure.client.endpoints;
    exports com.identisure.client.models;
}
