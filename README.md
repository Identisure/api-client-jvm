<img align="left" height="100" style="margin-right: 10px;" src="img/icon.png">

# Identisure JVM Client

[![Crate][crate-image]][crate-url]
[![Downloads][downloads-image]][downloads-url]
[![Documentation][docs-image]][docs-url]
[![MIT license][license-image]][license-url]

[crate-image]: https://img.shields.io/crates/v/identisure-client?style=flat-square
[crate-url]: https://crates.io/crates/identisure-client
[downloads-image]: https://img.shields.io/crates/d/identisure-client?style=flat-square
[downloads-url]: https://crates.io/crates/identisure-client
[docs-image]: https://img.shields.io/docsrs/identisure-client?style=flat-square
[docs-url]: https://docs.rs/identisure-client
[license-image]: https://img.shields.io/badge/license-MIT-blue?style=flat-square
[license-url]: https://opensource.org/licenses/MIT

API client for the [Identisure](https://identisure.com) REST API on the JVM (Java, Kotlin, Scala).

## Installation

### Gradle (Kotlin DSL)

```kotlin
// Java only
implementation("com.identisure:identisure-client-core:0.1.0")

// Kotlin coroutines extensions
implementation("com.identisure:identisure-client-kotlin:0.1.0")
```

### Maven

```xml
<dependency>
    <groupId>com.identisure</groupId>
    <artifactId>identisure-client-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

```java
import com.identisure.client.Environment;
import com.identisure.client.IdentisureClientBuilder;
import com.identisure.client.models.BankIdSeAuthRequest;
import com.identisure.client.models.BankIdSeStatus;

void main() {
    var client = new IdentisureClientBuilder("client_id", "client_secret")
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
import com.identisure.client.*
import com.identisure.client.models.*

suspend fun main() {
    val client = identisureClient("client_id", "client_secret") {
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

The tests use shared fixtures from a git submodule. Clone with `--recurse-submodules`, or run
`git submodule update --init` in an existing clone:

```sh
$ git clone --recurse-submodules https://github.com/identisure/api-client-jvm.git
```

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

#### Format and lint

Formatting is checked with Spotless (palantir-java-format for Java, ktfmt for Kotlin). Compiler warnings fail the build.

```sh
$ gradle spotlessCheck
$ gradle spotlessApply
```
