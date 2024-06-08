import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame{
    private JSONObject weatherData;

    public WeatherAppGUI(){
        // sets up GUI title
        super("Weather App");

        //ends program process once it has been closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set GUI size
        setSize(450,650);

        //load GUI in center of screen
        setLocationRelativeTo(null);

        //make layout manager null to manually position compenents within the GUI
        setLayout(null);

        //prevent GUI resize
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents(){
        //search field
        JTextField searchTextField = new JTextField();

        //set location and size of component
        searchTextField.setBounds(15, 15, 351, 45);

        //change font style and size
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);

        //weather image
        JLabel weatherConditionImage = new JLabel(loadImage("src\\assets\\cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        
        //center text
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        //weather condiitton description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        //humidity image
        JLabel humidityImage = new JLabel(loadImage("src\\assets\\humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66);
        add(humidityImage);

        //humidity text
            //sets text to be bold for certain parts
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //windspeed image
        JLabel windspeedImage = new JLabel(loadImage("src\\assets\\windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        //windspeed text
        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 100%</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        // search button
        JButton searchButton = new JButton(loadImage("src\\assets\\search.png"));

        //change cursor to hand cursor when hovering over button
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //get location from user
                String userInput = searchTextField.getText();

                //validate input and remove whitespace
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                //retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                //update GUI

                //update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                //update corresponding weather image depending on condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src\\assets\\clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src\\assets\\cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src\\assets\\rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src\\assets\\snow.png"));
                        break;
                }

                // update temperature text
                double temperature =(double) weatherData.get("temperature");
                temperatureText.setText(temperature + " F");

                //update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                //update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " +  humidity + "%</html>");

                //updatae windpseed text
                double windspeed = (long) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " +  windspeed + "km/h</html>");

                //
            }
        });
        add(searchButton);
    }

    // creates images in GUI components
    private ImageIcon loadImage(String resourcePath){
        try{
            //reads image file from given bath
            BufferedImage image = ImageIO.read(new File(resourcePath));

            //returns image icon to component for rendering
            return new ImageIcon(image);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }
}
