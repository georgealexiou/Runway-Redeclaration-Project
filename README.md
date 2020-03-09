# COMP2211 Software Engineering Group Project

Master : [![Build Status](https://travis-ci.com/georgealexiou/Software-Engineering-Group-Project.svg?token=8Vc6u46HsTP3dNwQiGD1&branch=master)](https://travis-ci.com/georgealexiou/Software-Engineering-Group-Project) 
Develop: [![Develop Build Status](https://travis-ci.com/georgealexiou/Software-Engineering-Group-Project.svg?token=8Vc6u46HsTP3dNwQiGD1&branch=develop)](https://travis-ci.com/georgealexiou/Software-Engineering-Group-Project)

## Installing local JavaFX

1. Visit this page: [OpenJFX docs](https://openjfx.io/openjfx-docs/#modular)
2. Follow the instructions to install JavaFX runtime and JavaFX mods

## Building the Application to a binary executable

```bash
mvn clean javafx:jlink
target/runwayredeclaration/bin/launcher 
```

## Running the Application

```bash
mvn clean javafx:run
```

## Building the Jar
```bash
mvn clean compile package
```

## Formatting with Maven
```bash
mvn formatter format
```

## Documentation

- [General Documentation](./documentation/general/README.md) 
  - [Log File](./documentation/general/LOG.md)
  - [Scenarios for GUI](./documentation/general/scenarios.md)

