package maze;

public class Square implements Comparable<Square>{
  private SquareType type;
  private int row;
  private int col;
  private int distance;

  public Square(SquareType type, int row, int col){
    this.type = type;
    this.row = row;
    this.col = col;
  }

  public SquareType getType(){
    return type;
  }

  public void markTried(){
    type = SquareType.TRIED;
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }

  public void setDistance(int distance){
    this.distance = distance;
  }

  public int getDistance(){
    return distance;
  }

  public int compareTo(Square other){
    return other.distance - this.distance;
  }

  public String toString(){
    return type.toString();
  }
}