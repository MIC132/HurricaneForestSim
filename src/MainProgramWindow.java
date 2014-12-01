import javax.swing.*;

public class MainProgramWindow {
    JFrame mainWindow;

    public MainProgramWindow() {
        mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setTitle("Forest wind damage simulation");
        mainWindow.setSize(500,500);
        mainWindow.setVisible(true);
    }
}
