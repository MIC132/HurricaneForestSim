import javax.swing.*;
import java.awt.*;

public class MainProgramWindow {
    JFrame mainWindow;
    DisplayPanel displayPanel;
    SideMenu sideMenu;
    Forest forest;

    public MainProgramWindow() {
        forest = new Forest();
        mainWindow = new JFrame();
        displayPanel = new DisplayPanel(forest);
        sideMenu = new SideMenu();
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setTitle("Forest wind damage simulation");
        mainWindow.setSize(500, 500);
        mainWindow.add(displayPanel, BorderLayout.CENTER);
        mainWindow.add(sideMenu,BorderLayout.EAST);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }
}
