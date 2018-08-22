/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.clickme.rac.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Harsha madushan
 */
public class Rent_a_car extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("/lk/clickme/rac/view/MainForm.fxml"));
            Scene mainScene = new Scene(root);
            
            primaryStage.setTitle("LK.CLICKME.RAC");
            primaryStage.setResizable(false);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Rent_a_car.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    public static void navigateToHome(Node node, Stage mainStage) {

    
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), node);
            tt.setFromX(0);
            tt.setToX(-node.getScene().getWidth());
            tt.play();
            
            Platform.runLater(()->{
            
                try {
                    Parent root = FXMLLoader.load(Rent_a_car.class.getResource("/lk/clickme/rac/view/MainForm.fxml"));
                    Scene mainScene = new Scene(root);
                    mainStage.setScene(mainScene);
                    
                    TranslateTransition tt1 = new TranslateTransition(Duration.millis(300), root.lookup("AnchorPane"));
                    tt1.setToX(0);
                    tt1.setFromX(-mainScene.getWidth());
                    tt1.play();
                    
                    mainStage.centerOnScreen();
                } catch (IOException ex) {
                    Logger.getLogger(Rent_a_car.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            });
            
  

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
