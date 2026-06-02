package se.idkollen.client

/** Create a client with a Kotlin DSL block. */
fun idkollenClient(clientId: String, clientSecret: String, block: IdkollenClientBuilder.() -> Unit = {}): IdkollenClient =
    IdkollenClientBuilder(clientId, clientSecret).apply(block).build()
