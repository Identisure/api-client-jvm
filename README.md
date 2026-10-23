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
import se.idkollen.client.Environment;
import se.idkollen.client.IdkollenClientBuilder;
import se.idkollen.client.models.BankIdSeAuthRequest;
import se.idkollen.client.models.BankIdSeStatus;

void main() {
    var client = new IdkollenClientBuilder("client_id", "client_secret")
        .environment(Environment.STAGING)
        .build();
    var bankIdSe = client.bankIdSe();

    bankIdSe.auth(new BankIdSeAuthRequest())
        .thenCompose(session -> bankIdSe.waitForAuth(((BankIdSeStatus.Pending) session).id))
        .thenAccept(result -> System.out.println(result))
        .join();
}
```

### Kotlin

```kotlin
import se.idkollen.client.*
import se.idkollen.client.endpoints.*
import se.idkollen.client.models.*

suspend fun main() {
    val client = idkollenClient("client_id", "client_secret") {
        environment(Environment.STAGING)
    }

    val session = client.bankIdSe().authAsync(BankIdSeAuthRequest()) as BankIdSeStatus.Pending
    val result = client.bankIdSe().waitForAuthAsync(session.id)
    println(result)
}
```

### Verifying callbacks

```java
boolean valid = CallbackSignature.verify(rawBody, request.getHeader("X-Signature-SHA256"), hmacSecret);
```

## Development

#### Prerequisites

* [JDK 17](https://adoptium.net/)
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
