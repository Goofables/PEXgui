package com.gmail.goofables.PEXgui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends Application {
   
   public static void main(String args[]) {
      
      Map<String, ArrayList<String>> permList = new HashMap<>();
      try {
         File file = new File("PEXgui\\permsList.list");
         file.setWritable(false);
         FileReader fReader = new FileReader(file);
         BufferedReader reader = new BufferedReader(fReader);
         String tmp;
         String head = "";
         while ((tmp = reader.readLine()) != null) {
            if (tmp.startsWith("<>")) {
               head = tmp.substring(2);
               permList.put(head, new ArrayList<>());
            } else {
               if (head.equals("")) throw new java.lang.NoSuchFieldException();
               permList.get(head).add(tmp);
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("Working directory: " + System.getProperty("user.dir"));
         System.out.println("Error! File not found. Please run `/pexGUI regen` to generate it.");
         return;
      } catch (IOException e) {
         System.out.println("Error! Could not read file. ");
         return;
      } catch (NoSuchFieldException e) {
         System.out.println("Error! Did not find a plugin section in permissions file. Please run `/pexGUI regen`.");
         return;
      }
      System.out.println("Opening window");
      launch(args);
      System.out.println("Window Closed");
   }
   
   @Override
   public void start(Stage window) throws Exception {
      window.setTitle("PEX GUI");
      
      Button closeBtn = new Button("Exit");
      closeBtn.setOnAction(e -> window.close());
      
      Pane layout1 = new Pane();
      layout1.getChildren().add(closeBtn);
      
      Scene mainScene = new Scene(layout1, 250, 250);
      window.setScene(mainScene);
      
      window.show();
      
   }
}