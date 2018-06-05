import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

public class Piece extends StackPane{

  private double previousxCoordinate;
  private double previousyCoordinate;
  private int xCoordinate;
  private int yCoordinate;
  private Circle circle;
  private int King = 0;
  private Color color;
  private int type;

  Piece(int yCoordinate, int xCoordinate, int type){
      if(type == 1 && King==0){
        color = Color.BLUE;
      }
      if(type == 0 && King==0){
        color = Color.RED;
      }
      move(xCoordinate, yCoordinate);
      this.yCoordinate = yCoordinate;
      this.xCoordinate = xCoordinate;
      Group n = createCircle(color);
      getChildren().addAll(n);
      colourChange(King);
      this.type = type;
      mouseIt();
      }

      public Group createCircle(Color color){
          circle = new Circle();
          circle.setCenterX(32.0f);
          circle.setCenterY(32.0f);
          circle.setRadius(24.0f);
          circle.setFill(color);
          Group noughts = new Group(circle);
          return noughts;
          }

      public void mouseIt(){
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent e) {
             if(e.isPrimaryButtonDown()!=true){
               circle.setFill(color);
              }
             if(e.isPrimaryButtonDown()==true){
               relocate(e.getSceneX(), e.getSceneY());
               circle.setFill(Color.DARKSLATEBLUE);
               }
             }};
       this.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);
       circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, eventHandler);
       circle.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);
     }

     public int getX(){
       return xCoordinate;
     }

     public int getY(){
       return yCoordinate;
     }

     public int getType(){
       return type;
     }

     public int getTypeK(){
       return King;
     }

     public void setType(int ytpe){
       type = ytpe;
     }

     public void setX(int x){ 
       xCoordinate = x;
     }

     public void setY(int y){
       yCoordinate = y;
     }

     public void colourChange(int King){
       if(King == 2){
         color = Color.BLUEVIOLET;
       }
       if(King == 3){
         color = Color.CRIMSON;
       }
     }

     public void changeTypeK(int type){  // Is this allowed
       King = type;
     }

     public void move(int x, int y){
       previousxCoordinate = x * 75 + 15;
       previousyCoordinate = y * 75 + 15;
       relocate(previousxCoordinate, previousyCoordinate);
     }
}
