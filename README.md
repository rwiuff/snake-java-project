# Snake Java Project for DTU course 02104
## Authors
Niklas Emil Lysdal

Nikolaj Vorndran Thygesen

Rasmus Kronborg Finnemann Wiuff
## About
This repo contains a JavaFX implementation of the classic game *Snake*.
## Branches
This repo has a main branch for the basic version of the game (basic requirements for passing the course). The **Advanced** branch contains the extended version (the *real* game).
## Usage
The program can be run from a terminal using the executable jar or the Gradle environment. To execute the jar, locate the file in the terminal and type:

```
java -jar Snake-<version number>.jar
```

To execute with torus dimensions, type:

```
java -jar Snake-<version number>.jar m n
```

With n and m representing the dimensions.
To run the program as a project, go to the root folder. Type the following for windows:

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