import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainProgramWindow {
    JFrame mainWindow;
    DisplayPanel displayPanel;
    SideMenu sideMenu;
    Forest forest;
    Random rng = new Random();

    public MainProgramWindow() {
        forest = new Forest();

        mainWindow = new JFrame();
        displayPanel = new DisplayPanel(this, forest);
        sideMenu = new SideMenu(this);
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setTitle("Forest wind damage simulation");
        mainWindow.setSize(1000, 600);
        mainWindow.add(displayPanel, BorderLayout.CENTER);
        mainWindow.add(sideMenu,BorderLayout.EAST);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        displayPanel.repaint();
    }

    public void run(){
        forest.speedExt = Double.parseDouble(sideMenu.speedField.getText());//Gets the speed from gui field
        forest.run();//Run Forrest, Run.
        displayPanel.repaint();
        sideMenu.okField.setText(String.valueOf(forest.getStateTrees(Tree.State.OK).size()));//This and next lines count and display the amount of trees in each state.
        sideMenu.brokenField.setText(String.valueOf(forest.getStateTrees(Tree.State.BROKEN).size()));
        sideMenu.uprootedField.setText(String.valueOf(forest.getStateTrees(Tree.State.UPROOTED).size()));
    }
}
