build:
    ./gradlew build

test:
    ./gradlew test

check:
    ./gradlew spotlessCheck compileJava compileTestJava compileKotlin

gen-doc:
    ./gradlew :core:javadoc
    rm -rf docs
    cp -r core/build/docs/javadoc docs
