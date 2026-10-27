@SuppressWarnings({"requires-automatic", "requires-transitive-automatic"}) // okhttp3 4.x ships no module descriptor
module com.identisure.client {
    requires transitive okhttp3;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires static transitive org.jspecify;

    exports com.identisure.client;
    exports com.identisure.client.models;
}
