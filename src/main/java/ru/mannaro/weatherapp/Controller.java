package ru.mannaro.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text humidity;

    @FXML
    private Text pressure;

    @FXML
    private Text sunrise;

    @FXML
    private Text sunset;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;




    @FXML
    void initialize() {
        city.setOnAction(event -> {
            getData.fire(); // Вызываем обработчик события для кнопки "Получить данные"
        });

        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=37bf02f98b81caee337dfc7129bfaa53&units=metric");
                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    //Преобразование температуры и вывод с одной цифрой после точки
                    double temp = obj.getJSONObject("main").getDouble("temp");
                    double feelsLike = obj.getJSONObject("main").getDouble("feels_like");
                    temp_info.setText("Температура:  " + String.format("%.1f", temp) + "°");
                    temp_feels.setText("Ощущается:  " + String.format("%.1f", feelsLike) + "°");


                    // Преобразование времени восхода солнца
                    long sunriseTime = obj.getJSONObject("sys").getLong("sunrise");
                    Instant sunriseInstant = Instant.ofEpochSecond(sunriseTime);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm")
                            .withZone(ZoneId.systemDefault());
                    String formattedSunrise = formatter.format(sunriseInstant);
                    sunrise.setText("Восход: " + formattedSunrise);

                    // Преобразование времени заката
                    long sunsetTime = obj.getJSONObject("sys").getLong("sunset");
                    Instant sunsetInstant = Instant.ofEpochSecond(sunsetTime);
                    String formattedSunset = formatter.format(sunsetInstant);
                    sunset.setText("Закат: " + formattedSunset);
                    // Преобразование давления и влажности
                    int pressure = (int) (obj.getJSONObject("main").getInt("pressure") * 0.750063755419211);
                    String result = "Давление: %dmm";
                    this.pressure.setText(result.formatted(pressure));
                    humidity.setText("Влажность:  " + obj.getJSONObject("main").getDouble("humidity") + "%");
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Такой город не был найден!");
        }
        return content.toString();
    }

}
