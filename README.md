# binaryMaple

[![Build Status](https://travis-ci.com/SimonIT/binaryMaple.svg?token=ZaZJqsZKYGqos74fJp9i&branch=master)](https://travis-ci.com/SimonIT/binaryMaple)

A project by [@codingWhale13](https://github.com/codingWhale13) and [@SimonIT](https://github.com/SimonIT)

## How to start

### Download

Go to the [Actions tab](https://github.com/SimonIT/binaryMaple/actions?query=workflow%3A%22Build+binaryMaple%22), click on the latest build and download the _snapshot.jar_ zip. In it, you will find two .jar files. Open the file with the suffix _-fat_ via double-click or via terminal `java -jar binaryMaple-1.0-SNAPSHOT-fat.jar`.

### CMD

The following maven command can be used: `mvn clean javafx:run -f pom.xml` or with debugging: `mvn clean compile exec:java -f pom.xml`

### If you use Intellij
The provided run configuration can be used or follow these steps:
1. Open the **Run/Debug Configuration** dialog 
2. Click the **Add New Configuration** button
3. Select **Maven**
4. Enter `clean javafx:run -f pom.xml` into the **Command Line** field
5. [Optional] Give it a nice name
