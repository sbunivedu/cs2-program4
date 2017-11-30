# MazeApp

We will add a GUI interface to our maze solver. You will need to clone this
repo and copy code from your maze solver.

MazeApp.java is added to be the entry point to our app. It implements the
```JFrame``` class and has a visual appearance. Study the source code to see
how it is constructed.

We will need to make the following changes to make the app work.
## Update Maze.java
* Update the constructor so that the maze can be updated with a new maze file on
demand.

```
  public Maze(){}

  public void loadFile(File file) throws FileNotFoundException{
	Scanner scan = new Scanner(file);
    cols = Integer.parseInt(scan.next());
    rows = Integer.parseInt(scan.next());

    grid = new Square[rows][cols];
...
```

* Make Maze class extend JPanel so it can have a visual appearance on screen.
```
public class Maze extends JPanel{
...
```

* add ```paintComponent``` method to allow the maze to draw itself.
```
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        Color color;
        switch(grid[i][j].getType()){
          case WALL:
          color = Color.BLACK;
          break;
          case START:
          color = Color.GREEN;
          break;
          case FINISH:
          color = Color.BLUE;
          break;
          case TRIED:
          color = Color.GRAY;
          break;
          case SPACE:
          color = Color.WHITE;
          break;
        default:
        color = Color.RED;
        }
        g.setColor(color);
        g.fillRect(20*j, 20*i, 20, 20);
      }
    }
  }
```

* Import new classes
```
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
```

## Update MazeSolver.java

* Update MazeSolver's main method to work with the new Maze constructor
```
    Maze maze = new Maze();
    maze.loadFile(new File(fileName));
```

* Refresh UI after visiting a square (marking it "tried") to update the maze on
  the screen:
```
        maze.repaint();
        try {
          Thread.sleep(500);
        } catch (InterruptedException ie){
        } finally{
          System.err.println("Pausing...");
        }
```

* Import classes
```
import java.io.File;
```

## Run Maze App
* Compile
```
javac maze/*.java
```
* Create a jar file
```
jar cvfm MazeApp.jar manifest.txt maze/*.class
```
* Download and run the jar file
You should be able to double click on the file to launch the app or execute
the jar file on commandline:
```
java -jar MazeApp.ja
```


