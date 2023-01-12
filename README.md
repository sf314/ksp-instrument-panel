# KRPC (Java) Flight Software

This project will contain rudimentary flight control automation, coupled with a
UI to display telemetry. The UI aims to be akin to a real aircraft's instrument
panel, featuring at a minimum the current airspeed, altitude, and compass
heading.

## Quickstart

The provided Makefile makes it easy for those who are familiar with the `make`
system:

```sh
make
make run
make clean
```

These are basically just aliases for gradle commands, i.e.:
```sh
./gradlew clean build run
```

## Project Startup Documentation

The following are the steps I took to initialize this project.

1: Install Gradle
```sh
brew install gradle
```

2: Initialize a new project using Gradle
- See: https://docs.gradle.org/current/samples/sample_building_java_applications.html
```sh
./gradlew init
```
- Setup as a application in Java, single-app, Groovy buildscript, Jupiter
  testing, with project name `krpcj-fsw`, and default package name `krpcj.fsw`

3: Create the wrapper (make things easy for later)
```sh
gradle wrapper
```
