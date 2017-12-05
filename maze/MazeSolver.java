package maze;
import java.io.FileNotFoundException;
import java.io.File;

public class MazeSolver{
  private Maze maze;
  private Agenda<Square> agenda;
  private boolean isPaused = false;

  public static void main(String[] args) throws FileNotFoundException{
    String fileName = args[0];
    Maze maze = new Maze();
    System.out.println("load maze from file: "+fileName);
    maze.loadFile(new File(fileName));
    //Agenda<Square> agenda = new MyStack<Square>();
    Agenda<Square> agenda = new AStar<Square>();
    MazeSolver solver = new MazeSolver(maze, agenda);
    boolean success = solver.solve();
    if(success == true){
      System.out.println("This maze is solved:");
      System.out.println(maze);
    }else{
      System.out.println("This maze is not solvable:");
      System.out.println(maze);
    }
  }

  public MazeSolver(Maze maze, Agenda<Square> agenda){
    this.maze = maze;
    this.agenda = agenda;
  }

  // return true if maze is solved, otherwise return false;
  public boolean solve(){
    addNeighbors(maze.getStart());
    while(!agenda.isEmpty()){
      Square next = agenda.remove();
      SquareType type = next.getType();
      if (type == SquareType.FINISH){
        return true;
      }else if (type == SquareType.SPACE){
        addNeighbors(next);
        next.markTried();
        animate(); // ask the maze to redraw itself
      }
    }
    return false;
  }

  // add neighbors of a square to the agenda
  private void addNeighbors(Square s){
    int row = s.getRow();
    int col = s.getCol();
    // neighbor to the north
    Square neighbor = maze.getSquare(row+1, col);
    if (neighbor != null && neighbor.getType() != SquareType.WALL){
      agenda.add(neighbor);
    }
    // neighbor to the south
    neighbor = maze.getSquare(row-1, col);
    if (neighbor != null && neighbor.getType() != SquareType.WALL){
      agenda.add(neighbor);
    }
    // neighbor to the east
    neighbor = maze.getSquare(row, col+1);
    if (neighbor != null && neighbor.getType() != SquareType.WALL){
      agenda.add(neighbor);
    }
    // neighbor to the west
    neighbor = maze.getSquare(row, col-1);
    if (neighbor != null && neighbor.getType() != SquareType.WALL){
      agenda.add(neighbor);
    }
  }

  synchronized private void animate(){
    if(isPaused){
      try{
        wait();
      } catch(InterruptedException e){}
    }
    maze.repaint();
    try{
      Thread.sleep(500);
    } catch (InterruptedException ie){}
  }

  synchronized public void pause(){
    isPaused = !isPaused;
    if(!isPaused){
      notify();
    }
  }

  public boolean isPaused(){
    return isPaused;
  }
}