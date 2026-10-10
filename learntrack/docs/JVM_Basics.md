# JVM Basics

## JDK, JRE and JVM

- **JVM (Java Virtual Machine)** is the program that actually runs Java bytecode. It loads classes,
  checks them, runs them and manages memory (garbage collection).
- **JRE (Java Runtime Environment)** is the JVM plus the standard class libraries (like `ArrayList`
  and `String`). It is what you need to *run* a Java program.
- **JDK (Java Development Kit)** is the JRE plus developer tools such as `javac` (the compiler), `jar`
  and `javadoc`. It is what you need to *write and build* Java programs.

A simple way to remember it: **JDK ⊃ JRE ⊃ JVM**.

## What is bytecode?

When you compile a `.java` file with `javac`, you do not get machine code for your specific computer.
You get **bytecode**, stored in a `.class` file. Bytecode is a compact, platform-neutral instruction
set that only the JVM understands. The JVM then translates it into instructions that the real
processor can run, using an interpreter and a JIT (just-in-time) compiler.

## "Write once, run anywhere"

This phrase means you write and compile your Java code a single time, and the same `.class` files
can run on Windows, Linux or macOS without changes.

It works because the compiled bytecode is the same everywhere
