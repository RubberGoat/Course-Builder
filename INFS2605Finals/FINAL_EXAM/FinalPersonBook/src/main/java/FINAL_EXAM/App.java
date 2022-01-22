package FINAL_EXAM;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    
    
    private void setPrimaryStage(Stage stage) {
        App.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return App.primaryStage;
    }

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, SQLException {
        // TIP: some customisations...
        stage.setResizable(false);
        
        setPrimaryStage(stage); 
        
        stage.getIcons().add(new Image("file:src/main/resources/FINAL_EXAM/Suzy.jpg"));
        stage.setTitle("Personbook");
        Database.setupDatabase();
        Database.insertData();
        Database.printAll();
        
//  
        
        // usual launch sequence
        scene = new Scene(loadFXML("primary"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
 
  
}