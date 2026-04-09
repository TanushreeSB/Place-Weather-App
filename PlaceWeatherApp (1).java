import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PlaceWeatherApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Place Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel locationLabel = new JLabel("Enter Location: ");
        JTextField locationField = new JTextField(20);
        JButton getWeatherButton = new JButton("Get Weather");
        JTextArea weatherTextArea = new JTextArea(10, 30);
        weatherTextArea.setEditable(false);

        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(getWeatherButton);
        panel.add(weatherTextArea);

        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String location = locationField.getText().trim();
                if (!location.isEmpty()) {
                    String apiKey = "a301a9da9dec12f7b394e5599cf244ff"; 
                    String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey;

                    try {
                        URL url = new URL(apiUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        Scanner scanner = new Scanner(connection.getInputStream());

                        StringBuilder response = new StringBuilder();
                        while (scanner.hasNextLine()) {
                            response.append(scanner.nextLine());
                        }

                        weatherTextArea.setText(response.toString());

                        scanner.close();
                        connection.disconnect();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        weatherTextArea.setText("Error fetching data");
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
