package com.gmail.goofables.PEXgui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application implements Runnable {
   @Override
   public void run() {
      System.out.println("Opening window");
      launch();
      System.out.println("Window Closed");
   }
   
   @Override
   public void start(Stage window) throws Exception {
      window.setTitle("PEX GUI");
      
      Button closeBtn = new Button("Exit");
      closeBtn.setOnAction(e -> window.close());
      
      VBox layout1 = new VBox();
      layout1.getChildren().add(closeBtn);
      Scene mainScene = new Scene(layout1, 250, 250);
      window.setScene(mainScene);
      
      window.show();
      
   }
}