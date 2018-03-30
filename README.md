# Invite  [![Build Status](https://travis-ci.org/fontourajunior/invite.svg?branch=master)](https://travis-ci.org/fontourajunior/invite)

[![Coverage Status](https://coveralls.io/repos/github/fontourajunior/invite/badge.svg?branch=master)](https://coveralls.io/github/fontourajunior/invite?branch=master)

Este é um projeto que visa o desenvolvimento de uma API, com intuito de criar um App, de um convite de eventos privados. O mesmo conta com as seguintes tecnologias para desenvolvimento:

  - Kotlin 1.2.20
  - Gradle 4.6
  - Spring Boot 1.5.10
  - Postgresql
  - Flyway-core
  - H2database
  - Docker
  - Docker Compose

O projeto utiliza um banco embarcado (H2) para desenvolvimento com profile de teste e para profile de desevolvimento está configurado inicialmente o Postgre, apesar de que outros bancos podem ser configurados facilmente.

O banco postgre esta configurado no docker compose.

## 1. Requisitos e Configurações

Para executar o projeto é necessária a instalação das seguintes ferramentas:

    1. JDK 1.8
    2. Gradle 4.6
    3. Docker 
    4. Docker Compose

[Download Doker](https://github.com/user/repo/blob/branch/other_file.md)
[Download Doker Compose](https://docs.docker.com/compose/install/)

## 2. Executando o Projeto
 
Após baixar o projeto, e as ferramentas necessárias, para executá-lo é necessário rodar os seguintes comandos dentro da pasta raiz.


```sh
$ gradle clean build
$ java -jar build/libs/invite-0.0.1-SNAPSHOT.jar ou 
```