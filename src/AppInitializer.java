import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

<<<<<<< HEAD
import java.io.*;
import java.util.Properties;
=======
import java.io.IOException;
import java.util.prefs.Preferences;
>>>>>>> feat/preferences

public class AppInitializer extends Application {

    private Properties prop = new Properties();   //access karanna lesi nisa File ekath methanama hadanawa
   private File propFile = new File("application.properties");   //danata me file eke store karala tyana okkoma properties tika uda tiyana Properties/prop object ekata load wenawa

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/EditorForm.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Simple Text Editor");
        loadProperties();

        double xPos;
        double yPos;
        double width;
        double height;

        try {
         xPos = Double.parseDouble(prop.getProperty("xPos", "-1"));
         yPos = Double.parseDouble(prop.getProperty("yPos", "-1"));
        }catch (NumberFormatException e){
         xPos=-1;
         yPos=-1;
        }

        try {
           width = Double.parseDouble(prop.getProperty("width", "-1"));
           height = Double.parseDouble(prop.getProperty("height", "-1"));
        }catch (NumberFormatException e){
            width=-1;
            height=-1;
        }


        if (width == -1 && height == -1){
           primaryStage.setMaximized(true);  //size eka
        }else {
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        }

        if (xPos==-1 && yPos==-1){
            primaryStage.centerOnScreen();
        }else {
            primaryStage.setX(xPos);
            primaryStage.setY(yPos);
        }


        primaryStage.show();
<<<<<<< HEAD
        primaryStage.setOnCloseRequest(event -> {
            prop.put("xPos",primaryStage.getX() + "");
            prop.put("yPos",primaryStage.getY() + "");

            if(!primaryStage.isMaximized()){
                prop.put("width",primaryStage.getWidth() + "");
                prop.put("height",primaryStage.getHeight() + "");
            }else {
                prop.put("width","-1");
                prop.put("height","-1");
            }

            try (FileOutputStream fos = new FileOutputStream(propFile);   //writing the data into afile
                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                prop.store(bos,null);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private void loadProperties() throws IOException {


       if (!propFile.exists()) return;

        try (FileInputStream fis = new FileInputStream(propFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
             prop.load(bis);

        } catch (IOException e) {
            e.printStackTrace();
        }

=======

        double xPos = Preferences.userRoot().node("simple-text-editor").getDouble("xPos",-1);
        double yPos = Preferences.userRoot().node("simple-text-editor").getDouble("yPos",-1);

        double width = Preferences.userRoot().node("simple-text-editor").getDouble("width",-1);
        double height = Preferences.userRoot().node("simple-text-editor").getDouble("height",-1);

        if(width == -1 && height == -1){
            primaryStage.setMaximized(true);
        }else {
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        }


        if(xPos == -1 && yPos == -1){
            primaryStage.centerOnScreen();
        }else {
            primaryStage.setX(xPos);
            primaryStage.setY(yPos);
        }

    primaryStage.setOnCloseRequest(event -> {
        Preferences.userRoot().node("simple-text-editor").putDouble("xPos",primaryStage.getX());
        Preferences.userRoot().node("simple-text-editor").putDouble("yPos",primaryStage.getY());
        Preferences.userRoot().node("simple-text-editor").putDouble("width",primaryStage.getWidth());
        Preferences.userRoot().node("simple-text-editor").putDouble("height",primaryStage.getHeight());
    });

>>>>>>> feat/preferences
    }
}
