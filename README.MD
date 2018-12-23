# Photo Bolter
This is application to make order photos heap.
To achieve this aimp place photo from one direstory to
another with obvious algorithm.

Algorithm is simple:

* Get create date from jpg file metadate
* Construct destination directory path according to create date
* Copy file by pressing `Enter` button on keyboard

Image from jpg file is shown before coping file. This feature allow
to make decision about necessary copy.

# Build project

To build you need [Maven](https://maven.apache.org/) and [jdk 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* Get project from github ```git clone git@github.com:shabanovve/photoBolter.git```
* Go to project directory cd  photoBolter
* Start maven build ```mvn clean compile assembly:single package```

# How to run

```java -jar target/photoBolter-1.0-SNAPSHOT-jar-with-dependencies.jar```
