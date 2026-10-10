# Setup Instructions

## JDK version used

- **JDK 21** (OpenJDK)

Check your own install with:

```bash
java -version
javac -version
```

## Installing the JDK

1. Download a JDK (for example Temurin / OpenJDK) 
2. Install it and make sure the `bin` folder is on your `PATH` (and `JAVA_HOME` is set if your IDE needs it).
3. Open a new terminal and run `javac -version` to confirm it works.

## Hello World

Create `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run:

```bash
javac HelloWorld.java     # produces HelloWorld.class (bytecode)
java HelloWorld           # the JVM runs the bytecode
```

Expected output:

```
Hello, World!
```

`javac` turns the source code into bytecode (`.class` file), and `java` starts the JVM, which loads and runs it.

> Screenshot: add a screenshot of your own terminal/IDE run here, e.g. `![Hello World run](images/hello_world.png)`.

## Running LearnTrack

See the [README](../README.md) for the compile and run commands.
