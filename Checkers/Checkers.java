import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;

public class Checkers extends Application{
  private SquareType [] [] arraySquare = new SquareType[8][8];
  private Group boardSquares = new Group();
  private BoardLogic bl = new BoardLogic();
  private Group pieces = new Group();
  private Group textEdit = new Group();
  private int squareSize = 75;
  private int boardSize = 8;
  private String colour;

  public Parent sceneBuilder(){
    Pane square = new Pane();
    square.setPrefSize(boardSize * squareSize, boardSize * squareSize);
    square.getChildren().addAll(boardSquares, pieces, textEdit);

    for(int i =0; i<boardSize; i++){
      for(int j = 0; j<boardSize; j++){
        if((j+i)%2!=0){
          colour = "W";
        }
        else{
          colour = "B";
        }
        SquareType pane = new SquareType(i, j , colour);
        Piece p = null;
        arraySquare[i][j] = pane;

        if(i==0 && j%2!=0 || i==1 && j%2==0 || i==2 && j%2!=0 ){
            p = createPiece(i, j, 0);
        }
        else if(i==5 && j%2==0 || i==6 && j%2!=0 || i==7 && j%2==0){
            p = createPiece(i, j, 1);
        }
        if(p!=null){
          pane.setPiece(p);
          pieces.getChildren().add(p);
        }
        boardSquares.getChildren().add(pane);
      }
    }
    return square;
  }

  public void text(){

        Text text = new Text();
        text.setFont(new Font(20));
        text.setX(250);
        text.setY(250);
        text.setTextAlignment(TextAlignment.CENTER);
      if(bl.check()==1){
        text.setFill(Color.ORANGE);
        text.setText("Blue won");
        textEdit.getChildren().add(text);
      }
      if(bl.check()==2){
        text.setFill(Color.ORANGE);
        text.setText("Red won");
        textEdit.getChildren().add(text);
      }
  }

  public Piece createPiece(int y, int x, int type){
    Piece p = new Piece(y, x, type);

      p.setOnMouseReleased(e -> {
      int xCoord = (int)(e.getSceneX()/75);
      int yCoord = (int)(e.getSceneY()/75);
      if(bl.validMove(p.getX(), p.getY(), xCoord, yCoord) || bl.takeMove(p.getX(), p.getY(), xCoord, yCoord, arraySquare, pieces, p)){
        p.move(xCoord, yCoord);
        p.setX(xCoord);
        p.setY(yCoord);
        arraySquare[p.getY()][p.getX()].setPieceNull();
        arraySquare[yCoord][xCoord].setPiece(p);
      }else{
        p.move(p.getX(),  p.getY());
      }
      text();
    });
    return p;
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    Scene scene = new Scene(sceneBuilder());
    Stage window = primaryStage;
    window.setTitle("CheckersFinalDraft");
    window.setScene(scene);
    window.show();
  }

  public static void main(String [] args){
  launch(args);
  }

}
