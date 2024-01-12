# Snake Java Project
The program can be run from a terminal using the executable jar or the Gradle environment. To execute the jar, locate the file in the terminal and type:

```
java -jar Snake-<version number>.jar
```

To execute with torus dimensions, type:

```
java -jar Snake-<version number>.jar n m
```

With n and m representing the dimensions.
To run the program as a project, go to the root folde. Type the following for windows:

```
.\gradlew.bat run
```

On unix machines type:

```
.\gradlew run
```

To execute with torus dimensions, type the following for windows:

```
.\gradlew.bat run --args "m n"
```

On unix machines type:

```
.\gradlew run --args "m n"
```