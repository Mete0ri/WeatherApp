import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.Font.BOLD;

public class Frame extends JFrame{
    private WeatherAPI weatherAPI = new WeatherAPI();
     public Frame(){
         setSize(400, 600);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setResizable(false);
         setLocationRelativeTo(null);
         setLayout(null);

         JLabel weatherConditionIcon = new JLabel();
         weatherConditionIcon.setBounds(125, 100, 150, 150);
         add(weatherConditionIcon);

         JLabel temperatureIcon = new JLabel();
         temperatureIcon.setBounds(60, 280, 100, 100);
         add(temperatureIcon);

         JLabel temperature = new JLabel();
         temperature.setFont(new Font("Arial", BOLD, 25));
         temperature.setBounds(170, 300, 100, 50);
         add(temperature);

         JTextField searchField = new JTextField();
         searchField.setBounds(0, 0, 400, 50);
         searchField.setFont(new Font("Ariel", BOLD, 25));
         searchField.setHorizontalAlignment(SwingConstants.CENTER);
         searchField.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String userInput = searchField.getText();

                 if(userInput.replaceAll("\\s", "").length() <= 0){
                     return;
                 }
                 double temp = weatherAPI.getTemperatur(userInput);
                 temperature.setText(temp + "°C");
                 if(temp > 20){
                     temperatureIcon.setIcon(addImage("src/assets/hot.png"));
                 }else if(temp <= 20 && temp >= 10){
                     temperatureIcon.setIcon(addImage("src/assets/temperature-average.png"));
                 }else{
                     temperatureIcon.setIcon(addImage("src/assets/temperature-cold.png"));
                 }
                 updateImage(weatherAPI.getWeatherCondition(userInput), weatherConditionIcon);

             }
         });
         add(searchField);
    }

    private ImageIcon addImage(String path){
        ImageIcon icon = new ImageIcon(path);
        return icon;
    }
    private void updateImage(String weatherCode, JLabel weatherConditionIcon){
        switch (weatherCode){
            case "Clear":
                weatherConditionIcon.setIcon(addImage("src/assets/sun.png"));
                break;
            case "Partly cloudy":
                weatherConditionIcon.setIcon(addImage("src/assets/cloudy-day.png"));
                break;
            case "Fog":
                weatherConditionIcon.setIcon(addImage("src/assets/mist.png"));
                break;
            case "Rainy":
                weatherConditionIcon.setIcon(addImage("src/assets/rain.png"));
                break;
            case "Snowy":
                weatherConditionIcon.setIcon(addImage("src/assets/snowy.png"));
                break;
            case "Storm":
                weatherConditionIcon.setIcon(addImage("src/assets/storm.png"));
                break;
        }
    }

}
