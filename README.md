# codeflix-admin-catalog

![App Status](https://github.com/fredsonchaves07/codeflix-admin-catalog/actions/workflows/cd-prod-workflow.yml/badge.svg)

🍿 admin catalog videos

## 📌 Content

- [About](#-about)
- [Technology](#-technology)
- [Installation and Configuration](#%EF%B8%8F-installation-and-configuration)
    - [Running with Docker](#running-with-docker)
    - [Installation of Dependencies](#installation-of-dependencies)
    - [Running application tests](#running-application-tests)
- [Running the Application](#%EF%B8%8F-running-the-application)
- [Docs](#-docs)
- [Issues](#-issues)
- [Contribution](#-contribution)
- [License](#%EF%B8%8F-license)
## 🚀 About

This repository contains the source code of the api that manages lists of movies and series. The technologies used are
described in Technology.

## 💻 Technology

- [Java 21](https://www.java.com/en/)
- [Maven](https://maven.apache.org/)
## 🛠️ Installation and Configuration

To execute the project in a development environment, it is necessary to have the tools installed. Can be consulted in
the technology section

### Running with Docker

This project can also be run by docker. You must have docker installed.
Run the command

```bash
docker-compose up --build
```

Access the api with the url [localhost:3000/api/v1](localhost:3000/api/v1)

### Installation of Dependencies

Run the command to perform the dependency installation

```bash
mvn clean install
```

### Running application tests


Run the command

```bash
mvn test
```

## ⚙️ Running the Application
Change the settings by putting your database credentials in .env file. See the [.env-example](https://github.com/fredsonchaves07/codeflix-admin-catalog/blob/main/.env-example) file for example.

This application uses flyway as a database migration tool.
Scripts are located [here](https://github.com/fredsonchaves07/movie-catch-api/tree/main/src/main/resources/db/migration)

Run Application in development mode after installation and configuration

```bash
mvn spring-boot:run
```

Access the api with the url [localhost:3000/api/v1](localhost:8080/api/v1)

To access API documentation [localhost:3000/api/v1/docs](localhost:8080/docs)

## 📝 Docs

Application documents and files can be found in the `docs` directory.

Some of the types of documents that can be found.

- Entity and database tables in `database`
- Application flow and use cases in `application`
- Collections of postman requests in `collection`

To access the running API swagger documents. Access a url `/docs`

## 🐛 Issues

I would love to review your pull request! Open a new [issue](https://github.com/fredsonchaves07/movie-catch-api/issues)

## 🤝 Contribution

Feel free to contribute to the project. I am open for suggestions.
Click [here](https://github.com/fredsonchaves07/movie-catch-api/issues) to open a new issue or take part in the
development [project](https://github.com/fredsonchaves07/movie-catch-api/projects/1) 😄

## ⚖️ License

This project uses MIT License. Click [here](https://github.com/fredsonchaves07/movie-catch-api/blob/main/LICENSE) to
access

---
Developed 💙 by Fredson Chaves