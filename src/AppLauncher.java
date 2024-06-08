import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        //invokeLater() allows for updates to the GUi to be more thread safe
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            
            // instantiates and displays GUi
            public void run(){
                //displays weather app GUI
                new WeatherAppGUI().setVisible(true);

                System.out.println(WeatherApp.getLocationData("Tokyo"));
            }
        });
    }
}
