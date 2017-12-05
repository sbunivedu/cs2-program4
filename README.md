# MazeApp

A GUI interface has been added to our maze solver from last programming
assignment. Most of UI logic resides in ```MazeApp.java```, which is the new
entry point to our program. It implements the ```JFrame``` class and has a
visual appearance. Study the source code to see how it is constructed.

We will add a new agenda so that the maze can be solved faster. The new agenda
implements the [A* search algorithm](
https://en.wikipedia.org/wiki/A*_search_algorithm). As other agendas we emplay
"A*" allows us to added new squares to explore and remove the next square to
explore, but it does it according to some heuristics, which is the Euclidean
distance between a square to the finish location (shortest first).

The modified ```Square``` class stores the distance and implements its
```compareTo()``` according to such distances. You need to implement the new
```AStar``` aggenda the provided ```Heap``` class.

## Run Maze App
* Compile
```
javac maze/*.java
```
* Create a jar file
```
jar cvfm MazeApp.jar manifest.txt maze/*.class maze/exceptions/*.class
```
* Download and run the jar file
You should be able to double click on the file to launch the app or execute
the jar file on commandline:
```
java -jar MazeApp.ja
```

## Experiment with Maze App
Run the app with different maze configuration and agendas to understand its
behavior. You can pause and unpause by pressing the key for letter "p".

Demonstrate your understanding by correctly predicting the next move of the
solver.

