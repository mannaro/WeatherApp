package ru.mannaro.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("weather.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 690);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setResizable(false);
        stage.setTitle("Погода");
        stage.setScene(scene);
        stage.getIcons().add(new Image(new FileInputStream(Path.of("src","main", "resources", "ru", "mannaro", "weatherapp", "images.jpeg").toFile())));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}