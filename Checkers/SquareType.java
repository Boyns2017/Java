import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class SquareType extends Rectangle{

  private int squareSize = 75;
  private Piece p = null;

  SquareType(int yCoordinate, int xCoordinate, String symbol){
    setWidth(squareSize);
    setHeight(squareSize);
    relocate(xCoordinate*squareSize, yCoordinate*squareSize);
    if(symbol.equalsIgnoreCase("W")){
        setFill(Color.WHITE);
    }else{
        setFill(Color.BLACK);
  }
  }

  public boolean setPiece(Piece p){ //is this allowed
    this.p = p;
    return true;
  }

  public boolean setPieceNull(){  //is this allowed
    this.p = null;
    return true;
  }

  public Piece getPiece(){
    return p;
  }

  public boolean piecePresent(){
    if(p!=null){
      return true;
    }
    return false;
  }

}
