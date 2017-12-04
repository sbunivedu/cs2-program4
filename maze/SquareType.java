package maze;

public enum SquareType{
  WALL('#'),
  SPACE('.'),
  START('*'),
  FINISH('o'),
  TRIED('x'),
  PATH(' ');

  private char mark;

  SquareType(char c){
    mark = c;
  }

  public String toString(){
    return ""+mark;
  }

  public static SquareType fromChar(char c){
    switch(c){
      case '#':
        return WALL;
      case '.':
        return SPACE;
      case '*':
        return START;
      case 'o':
        return FINISH;
      case 'x':
        return TRIED;
      case ' ':
        return PATH;
      default:
        throw new IllegalArgumentException();
    }
  }
}
