import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.awt.Font.BOLD;

public class Frame extends JFrame{
     public Frame(){
         setSize(400, 600);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         setResizable(false);
         setLocationRelativeTo(null);
         setLayout(null);

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
             }
         });
         add(searchField);

         JLabel sun = new JLabel(addImage("src/assets/sun.png"));
         sun.setBounds(125, 100, 150, 150);
         add(sun);

         JLabel temperatureIcon = new JLabel(addImage("src/assets/hot.png"));
         temperatureIcon.setBounds(60, 280, 100, 100);
         add(temperatureIcon);

         JLabel temperature = new JLabel();
         temperature.setText("20°C");
         temperature.setFont(new Font("Arial", BOLD, 25));
         temperature.setBounds(170, 300, 100, 50);

         add(temperature);

    }
    private ImageIcon addImage(String path){
        ImageIcon icon = new ImageIcon(path);
        return icon;
    }

}
