package com.identisure.client

/** Create a client with a Kotlin DSL block. */
fun identisureClient(
    clientId: String,
    clientSecret: String,
    block: IdentisureClientBuilder.() -> Unit = {},
): IdentisureClient = IdentisureClientBuilder(clientId, clientSecret).apply(block).build()
