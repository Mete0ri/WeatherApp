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
         weatherConditionIcon.setBounds(125, 120, 150, 150);
         add(weatherConditionIcon);

         JLabel temperatureIcon = new JLabel();
         temperatureIcon.setBounds(60, 320, 100, 100);
         add(temperatureIcon);

         JLabel temperatureText = new JLabel();
         temperatureText.setFont(new Font("Arial", BOLD, 33));
         temperatureText.setBounds(150, 340, 100, 50);
         temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
         add(temperatureText);

         JLabel conditionText = new JLabel();
         conditionText.setFont(new Font("Arial", BOLD, 20));
         conditionText.setBounds(50, 250, 300, 50);
         conditionText.setHorizontalAlignment(SwingConstants.CENTER);
         add(conditionText);

         JLabel dayIcon = new JLabel();
         dayIcon.setBounds(168, 430, 100, 100);
         add(dayIcon);

         JLabel dayText = new JLabel();
         dayText.setFont(new Font("Arial", BOLD, 15));
         dayText.setBounds(175, 520, 50, 15);
         dayText.setHorizontalAlignment(SwingConstants.CENTER);
         add(dayText);

         JLabel dataText = new JLabel();
         dataText.setFont(new Font("Arial", BOLD, 20));
         dataText.setBounds(10, 10, 150, 20);
         add(dataText);

         JLabel houreText = new JLabel();
         houreText.setFont(new Font("Arial", BOLD, 20));
         houreText.setBounds(340, 10, 100, 20);
         add(houreText);

         JTextField searchField = new JTextField("Localization");
         searchField.setBounds(50, 40, 300, 50);
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
                 temperatureText.setText(temp + "°C");
                 if(temp > 20){
                     temperatureIcon.setIcon(addImage("src/assets/hot.png"));
                 }else if(temp <= 20 && temp >= 10){
                     temperatureIcon.setIcon(addImage("src/assets/temperature-average.png"));
                 }else{
                     temperatureIcon.setIcon(addImage("src/assets/temperature-cold.png"));
                 }
                 updateWeatherImage(weatherAPI.getWeatherCondition(userInput), weatherConditionIcon, conditionText);
                 updateDayIcone(weatherAPI.getDay(userInput), dayIcon, dayText);
                 String[] data = dataConverter(weatherAPI.getTime(userInput));
                 dataText.setText(data[0]);
                 houreText.setText(data[1]);
             }
         });
         add(searchField);
    }

    private ImageIcon addImage(String path){
        ImageIcon icon = new ImageIcon(path);
        return icon;
    }
    private void updateWeatherImage(String weatherCode, JLabel weatherConditionIcon, JLabel conditionText){
        switch (weatherCode){
            case "Cloudy":
                weatherConditionIcon.setIcon(addImage("src/assets/sun.png"));
                conditionText.setText("Clody");
                break;
            case "Partly cloudy":
                weatherConditionIcon.setIcon(addImage("src/assets/cloudy-day.png"));
                conditionText.setText("Partly cloudy");
                break;
            case "Fog":
                weatherConditionIcon.setIcon(addImage("src/assets/mist.png"));
                conditionText.setText("Fogy");
                break;
            case "Rainy":
                weatherConditionIcon.setIcon(addImage("src/assets/rain.png"));
                conditionText.setText("Rainy");
                break;
            case "Snowy":
                weatherConditionIcon.setIcon(addImage("src/assets/snowy.png"));
                conditionText.setText("Snowy");
                break;
            case "Storm":
                weatherConditionIcon.setIcon(addImage("src/assets/storm.png"));
                conditionText.setText("Stormy");
                break;
        }
    }
    private void updateDayIcone(char dayCode, JLabel dayIcon, JLabel dayText){
         switch(dayCode){
             case 'N':
                 dayIcon.setIcon(addImage("src/assets/moon.png"));
                 dayText.setText("Night");
                 getContentPane().setBackground(new Color(0, 77, 120));
                 break;
             case 'D':
                 dayIcon.setIcon(addImage("src/assets/daytime.png"));
                 dayText.setText("Day");
                 getContentPane().setBackground(new Color(209, 255, 255));
                 break;
         }
    }
    private String[] dataConverter(String data){
         String[] parts = data.split("T");
         return parts;
    }

}
