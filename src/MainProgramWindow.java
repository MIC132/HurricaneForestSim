import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainProgramWindow {
    JFrame mainWindow;
    DisplayPanel displayPanel;
    SideMenu sideMenu;
    Forest forest;
    Random rng;

    public MainProgramWindow() {
        forest = new Forest();
        rng = new Random();
        for(int i=0;i<50;i++) {
            forest.trees.add(new ScotsPine(rng.nextInt(400), rng.nextInt(500), 5, 0.5, 15, 10));
        }

        mainWindow = new JFrame();
        displayPanel = new DisplayPanel(this, forest);
        sideMenu = new SideMenu(this);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setTitle("Forest wind damage simulation");
        mainWindow.setSize(500, 500);
        mainWindow.add(displayPanel, BorderLayout.CENTER);
        mainWindow.add(sideMenu,BorderLayout.EAST);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        displayPanel.repaint();
    }

    public void run(){
        forest.run();
        displayPanel.repaint();
    }
}
