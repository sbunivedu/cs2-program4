package maze;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

public class Maze extends JPanel{
  private int rows;
  private int cols;
  private Square start;
  private Square finish;
  private Square[][] grid;

  public Maze(){}

  public void loadFile(File file) throws FileNotFoundException{
    Scanner scan = new Scanner(file);
    cols = Integer.parseInt(scan.next());
    rows = Integer.parseInt(scan.next());

    grid = new Square[rows][cols];

    // remove line break;
    scan.nextLine();

    // read maze configuration
    for(int i=0; i<rows; i++){
      String row = scan.nextLine();
      for(int j=0; j<cols; j++){
        // create a square from the char representation
        SquareType type = SquareType.fromChar(row.charAt(j));
        Square s = new Square(type, i, j);
        grid[i][j] = s;

        // remember the square as "start" if it is the starting point
        if(type == SquareType.START){
          start = s;
        }
        if(type == SquareType.FINISH){
          finish = s;
        }
        //System.out.print(row.charAt(j));
      }
      //System.out.println();
    }

    // calculate heuristic: euclidean distance to finish
    int finishRow = finish.getRow();
    int finishCol = finish.getCol();
    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        int distance = (finishRow - i)*(finishRow - i) +
                       (finishCol - j)*(finishCol - j);
        grid[i][j].setDistance(distance);
      }
    }
  }

  public int getRows(){
    return rows;
  }

  public int getColumns(){
    return cols;
  }

  public Square getStart(){
    return start;
  }

  // return null if the location is out of bounds.
  public Square getSquare(int row, int col){
    if(row < 0 || row >= rows || col < 0 || col >= cols){
      return null;
    }else{
      return grid[row][col];
    }
  }

  public String toString(){
    String result = "";
    for(int i=0; i<rows; i++){
      for(int j=0; j<cols; j++){
        result += grid[i][j];
      }
      result += "\n";
    }

    return result;
  }

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
}