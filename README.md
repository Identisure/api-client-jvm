<img align="left" height="100" style="margin-right: 10px;" src="img/icon.png">

# Identisure JVM Client

[![Maven Central][maven-image]][maven-url]
[![Documentation][docs-image]][docs-url]
[![MIT license][license-image]][license-url]

[maven-image]: https://img.shields.io/maven-central/v/com.identisure/identisure-client?style=flat-square
[maven-url]: https://central.sonatype.com/artifact/com.identisure/identisure-client
[docs-image]: https://javadoc.io/badge2/com.identisure/identisure-client/javadoc.svg?style=flat-square
[docs-url]: https://javadoc.io/doc/com.identisure/identisure-client
[license-image]: https://img.shields.io/badge/license-MIT-blue?style=flat-square
[license-url]: https://opensource.org/licenses/MIT

API client for the [Identisure](https://developers.identisure.com) REST API on the JVM (Java, Kotlin, Scala).

## Requirements

Java 17 or later.

## Supported providers

- **BankID SE**: auth, sign, phone auth and sign, QR verify, age verification
- **BankID NO**: auth, backchannel auth, sign, age verification
- **Freja eID**: auth, backchannel auth, sign, backchannel sign, age verification
- **MitID**: auth, backchannel auth, sign, age verification
- **FTN** (Finnish Trust Network): auth, age verification
- **Vipps MobilePay**: auth, backchannel auth
- **Documents**: upload and download documents for signing

## Installation

### Gradle (Kotlin DSL)

```kotlin
// Java only
implementation("com.identisure:identisure-client:0.1.0")

// Kotlin coroutines extensions
implementation("com.identisure:identisure-client-kotlin:0.1.0")
```

### Maven

```xml
<dependency>
    <groupId>com.identisure</groupId>
    <artifactId>identisure-client</artifactId>
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

## Other languages

[C#](https://github.com/identisure/api-client-csharp), [Dart](https://github.com/identisure/api-client-dart), [Go](https://github.com/identisure/api-client-go), [TypeScript/JavaScript](https://github.com/identisure/api-client-js), [PHP](https://github.com/identisure/api-client-php), [Python](https://github.com/identisure/api-client-py), [Ruby](https://github.com/identisure/api-client-ruby), [Rust](https://github.com/identisure/api-client-rust), [Swift](https://github.com/identisure/api-client-swift)
