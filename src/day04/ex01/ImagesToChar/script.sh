#!/bin/bash

find . -name "*.java" > sources.txt
mkdir target && mkdir target/edu && mkdir target/edu/school21 && mkdir target/edu/school21/printer
mkdir target/edu/school21/printer/app && mkdir target/edu/school21/printer/logic
javac -d target @sources.txt
cp -R src/resources target/
jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target edu -C src resources
java -jar target/images-to-chars-printer.jar . 0