module se.idkollen.client {
    requires okhttp3;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires static org.jspecify;

    exports se.idkollen.client;
    exports se.idkollen.client.endpoints;
    exports se.idkollen.client.models;
}
