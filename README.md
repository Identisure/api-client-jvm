<img align="left" height="100" style="margin-right: 10px;" src="../img/icon.png">

# IDkollen JVM Client

[![Crate][crate-image]][crate-url]
[![Downloads][downloads-image]][downloads-url]
[![Documentation][docs-image]][docs-url]
[![MIT license][license-image]][license-url]

[crate-image]: https://img.shields.io/crates/v/idkollen-client?style=flat-square
[crate-url]: https://crates.io/crates/idkollen-client
[downloads-image]: https://img.shields.io/crates/d/idkollen-client?style=flat-square
[downloads-url]: https://crates.io/crates/idkollen-client
[docs-image]: https://img.shields.io/docsrs/idkollen-client?style=flat-square
[docs-url]: https://docs.rs/idkollen-client
[license-image]: https://img.shields.io/badge/license-MIT-blue?style=flat-square
[license-url]: https://opensource.org/licenses/MIT

API client for the [IDkollen](https://idkollen.se) REST API on the JVM (Java, Kotlin, Scala).

## Installation

### Gradle (Kotlin DSL)

```kotlin
// Java only
implementation("se.idkollen:idkollen-client-core:0.1.0")

// Kotlin coroutines extensions
implementation("se.idkollen:idkollen-client-kotlin:0.1.0")
```

### Maven

```xml
<dependency>
    <groupId>se.idkollen</groupId>
    <artifactId>idkollen-client-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

```java
import se.idkollen.client.IdkollenClient;
import se.idkollen.client.IdkollenClientBuilder;
import se.idkollen.client.PollOptions;
import se.idkollen.client.endpoints.BankIdSeEndpoint;
import se.idkollen.client.models.BankIdSeAuthRequest;

void main() {
    var client = new IdkollenClientBuilder("client_id", "client_secret")
        .environment(Environment.STAGING)
        .build();
    
    client.bankIdSe().auth(new BankIdSeAuthRequest())
        .thenCompose(session -> bankIdSe.waitForAuthAsync(session.id(), new PollOptions()))
        .thenAccept(result -> System.out.println(result));
}
```

### Kotlin

```kotlin
import se.idkollen.client.*
import se.idkollen.client.endpoints.*
import se.idkollen.client.models.*

val client = idkollenClient("client_id", "client_secret") {
    environment(Environment.STAGING)
}

val session = client.bankIdSe().auth(BankIdSeAuthRequest())
val result  = client.bankIdSe().waitForAuth(session.id())
println(result)
```

## Development

#### Prerequisites

* [Java 17+](https://www.oracle.com/java/technologies/downloads/)
* [Kotlin 2+](https://kotlinlang.org/)

#### Build

To compile the project, simply issue the following command:

```sh
$ gradle compileJava
$ gradle compileKotlin
```

#### Test

##### Unit Testing 

```sh
$ gradle test
```
