#!/bin/bash

# Aviso: é necessário ter o maven instalado
# Ciente das particularidades dos desenvolvedores deste projeto, sugiro o comando:
# $ yes y | yaourt -S maven

mvn generate-resources
mvn clean package
cd target/
java -jar $(ls . | grep *.jar)
