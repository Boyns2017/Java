import javafx.scene.*;

public class BoardLogic{
  private String [][] boardPosition = new String[8][8];
  private String [][] boardColours = new String[8][8];
  private String enemy2 = "K";
  private String enemy = "O";
  private int testNumber = 0;
  private int trigger = 0;
  private String s = "X";
  private int reset = 0;
  private int move = 0;

  BoardLogic(){
    for(int i=0; i<8; i++){
      for(int j = 0; j<8; j++){
        if((j+i)%2!=0){
          boardColours[i][j] = "W";
        }
        else{
          boardColours[i][j] = "B";
        }
        if(i==0 && j%2!=0 || i==1 && j%2==0 || i==2 && j%2!=0 ){
          boardPosition[i][j] = "X";
        }
        else if(i==5 && j%2==0 || i==6 && j%2!=0 || i==7 && j%2==0){
          boardPosition[i][j] = "O";
        }
        else{
          boardPosition[i][j] = "I";
        }
      }
    }
  }

    public int check(){
      int counterX = 0;
      int counterO = 0;
      for(int i=0; i<8; i++){
        for(int j = 0; j<8; j++){
          if((j+i)%2!=0){
            if(boardPosition[i][j].equals("X") || boardPosition[i][j].equals("Q")){
              counterX++;
            }
            if(boardPosition[i][j].equals("O") || boardPosition[i][j].equals("K")){
              counterO++;
            }
          }
        }
      }
    if(counterX==0){
      return 1;
    }
    if(counterO==0){
      return 2;
    }
      return 0;
  }


  public boolean withInBounds(int moveX, int moveY){
      if(moveX>=0 && moveY<8 && moveX<8 && moveY>=0){
        return true;
      }
    return false;
  }

  public boolean viewBoard(){
      for(int i=0; i<8; i++){
        for(int j = 0; j<8; j++){
          System.out.print("|"+boardPosition[i][j]+"|");
      }
      System.out.println("\n");
    }
    System.out.println("Next Move\n");
    return true;
  }

  public boolean kingMaker(int moveX, int moveY,  Piece p){
    if(moveY==0  && p.getType()==1){
      boardPosition[moveY][moveX] = "K";
      p.changeTypeK(2);
      p.colourChange(2);
      p.setType(4);
      return true;
    }
    if(moveY==7  && p.getType()==0){
      boardPosition[moveY][moveX] = "Q";
      p.colourChange(3);
      p.changeTypeK(3);
      p.setType(6);
      return true;
    }
    return false;

  }

  public boolean setUnits(Piece p, int currentY, int moveY){
    if(withInBounds(0, moveY)){
    if((p.getType()==0  && p.getTypeK()==0 )|| p.getType()==6 && (currentY-moveY<0)){        // Crosses && case for double cross
      enemy2 = "K";
      enemy = "O";
      trigger = -1;
      reset = -1;
      move = -2;
      if(p.getType()==0){
        s = "X";
      }
      if(p.getType()==6 && (currentY-moveY<0)){
        s = "Q";
      }
      return true;
    }

    if((p.getType()==1 && p.getTypeK()==0) || (p.getType()==4 && (currentY-moveY>0))){          // Noughts  && case for double noughts
      reset = +1;
      move = +2;
      trigger = 1;
      if(p.getType()==1){
        s = "O";
      }
      if(p.getType()==4 && (currentY-moveY>0)){
        s = "K";
      }
      enemy = "X";
      enemy2 = "Q";
      return true;
    }
    if(p.getTypeK()==2 && (currentY-moveY<0)){    // Double O
      reset = -1;
      move = -2;
      s = "K";
      enemy = "X";
      enemy2 = "Q";
      trigger = -1;
      return true;
    }
    if(p.getTypeK()==3 && (currentY-moveY>0)){      // Double X
      reset = +1;
      move = +2;
      s = "Q";
      enemy = "O";
      enemy2 = "K";
      trigger = +1;
      return true;
    }
  }
    return false;
  }

  public boolean takeMove(int currentX, int currentY, int moveX, int moveY, SquareType [][] squareGrid, Group pieces, Piece p){
    setUnits(p, currentY, moveY);
    kingMaker(currentX, currentY, p);
    if(moveX+2 == currentX || moveX-2 == currentX){
        if(boardPosition[currentY][currentX].equals(s)){
          if(moveY+move == currentY){
            if(((withInBounds(moveY, moveX-1) && (boardPosition[moveY+reset][moveX-1].equals(enemy) || boardPosition[moveY+reset][moveX-1].equals(enemy2))) || (withInBounds(moveY, moveX+1) && (boardPosition[moveY+reset][moveX+1].equals(enemy) || boardPosition[moveY+reset][moveX+1].equals(enemy2))))){
              if(currentX - moveX < 0){
                boardPosition[moveY+trigger][moveX-1] = "I";
                pieces.getChildren().remove(squareGrid[moveY+trigger][moveX-1].getPiece());
              }else{
                boardPosition[moveY+trigger][moveX+1] = "I";
                pieces.getChildren().remove(squareGrid[moveY+trigger][moveX+1].getPiece());
              }
              boardPosition[currentY][currentX] = "I";
              boardPosition[moveY][moveX] = s;
              return true;
            }
        }
      }
    }
    return false;
    }

  public boolean validMove(int currentX, int currentY, int moveX, int moveY){

  if(moveX+1 == currentX || moveX-1 == currentX){
    if(boardPosition[currentY][currentX].equals("X") || boardPosition[currentY][currentX].equals("K") || boardPosition[currentY][currentX].equals("Q")){
    if(moveY-1 == currentY){
      if(boardPosition[moveY][moveX].equals("O")){
        return false;
      }
      if(boardPosition[moveY][moveX].equals("X")){
        return false;
      }
      if(boardPosition[currentY][currentX].equals("X")){
        boardPosition[moveY][moveX] = "X";
      }
      if(boardPosition[currentY][currentX].equals("Q")){
          boardPosition[moveY][moveX] = "Q";
      }
      if(boardPosition[currentY][currentX].equals("K")){
        boardPosition[moveY][moveX] = "K";
      }
        boardPosition[currentY][currentX] = "I";
        return true;
      }}

    if(boardPosition[currentY][currentX].equals("O") || boardPosition[currentY][currentX].equals("K") || boardPosition[currentY][currentX].equals("Q")){
      if(moveY+1 == currentY){
        if(boardPosition[moveY][moveX].equals("X")){
          if(boardPosition[moveY][moveX].equals("X")){
              return false;
          }
          if(currentY+2 == moveY){
            if(currentX+2 == moveX|| currentY+2 == moveX){
              return true;
            }
          }
        }
        if(boardPosition[moveY][moveX].equals("O")){
          return false;
        }
        if(boardPosition[currentY][currentX].equals("O")){
          boardPosition[moveY][moveX] = "O";
        }
        if(boardPosition[currentY][currentX].equals("Q")){
            boardPosition[moveY][moveX] = "Q";
        }
        if(boardPosition[currentY][currentX].equals("K")){
          boardPosition[moveY][moveX] = "K";
        }
          boardPosition[currentY][currentX] = "I";
        return true;
      }
    }
  }
  return false;
  }

  boolean claim(boolean boo) {
    boolean b = false;
    b = boo;
      if (!b) throw new Error("Test " + testNumber + " fails");
      testNumber++;
    return true;}

  public void testIt(){
    Piece testP = new Piece(3, 2, 1);
    Piece testP2 = new Piece(3, 2, 1);
    //In Bounds
    claim(withInBounds(1, 7));
    claim(withInBounds(3, 6));
    claim(withInBounds(4, 5));
    claim(withInBounds(1, 2));
    //Out of bounds
    claim(!withInBounds(10, 9));
    claim(!withInBounds(11, 12));
    claim(!withInBounds(1200, 2));
    claim(!withInBounds(21, 102));
    //View Board
    claim(viewBoard());
    //King Maker ie Double piece
    claim(kingMaker(1, 0, testP2));
    claim(!kingMaker(7, 7, testP2));
    claim(kingMaker(1, 0, testP));
    claim(!kingMaker(7, 7, testP));
    claim(!kingMaker(24, 4, testP));
    claim(!kingMaker(32, 2, testP2));
    //Controls the movement type
    claim(setUnits(testP, 2, 7));
    claim(setUnits(testP, 2, 0));
    claim(!setUnits(testP2, 2, 29));
    claim(!setUnits(testP2, 5, 27));
    claim(!setUnits(testP2, 3, 1000));
    claim(!setUnits(testP2, 1, 17));
    //Valid moves
    claim(!validMove(0, 1, 1, 1));
    claim(!validMove(0, 3, 4, 1));
    claim(!validMove(7, 6, 5, 6));
    claim(!validMove(7, 7, 5, 6));
    claim(!validMove(5, 3, 4, 6));
    claim(!validMove(5, 5, 6, 6));
    claim(validMove(0, 5, 1, 4));
    claim(!validMove(0, 5, 7, 4));
    claim(validMove(5, 2, 6, 3));
    claim(!validMove(5, 2, 6, 9));
    claim(validMove(1, 2, 2, 3));
    claim(!validMove(1, 2, 2, 8));


  }

  public static void main(String [] args){
    BoardLogic logicTest = new BoardLogic();
    logicTest.testIt();
  }


}
