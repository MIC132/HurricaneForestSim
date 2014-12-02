import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenu extends JPanel {
    final MainProgramWindow mainWindow;
    JButton runButton;
    JButton add10;
    JButton add100;
    JTextField speedField;
    public SideMenu(MainProgramWindow parent) {
        this.mainWindow = parent;
        this.setPreferredSize(new Dimension(100,500));
        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.run();
                //runButton.setEnabled(false);
                //speedField.setEnabled(false);
            }
        });
        runButton.setMaximumSize(new Dimension(100,50));
        this.add(runButton);

        add10 = new JButton("Add 10");
        add10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.forest.addRandomTrees(10,400,500);
                mainWindow.displayPanel.repaint();
            }
        });
        add10.setMaximumSize(new Dimension(100,50));
        this.add(add10);

        add100 = new JButton("Add 100");
        add100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.forest.addRandomTrees(100,400,500);
                mainWindow.displayPanel.repaint();
            }
        });
        add100.setMaximumSize(new Dimension(100,50));
        this.add(add100);

        speedField = new JTextField();
        speedField.setMaximumSize(new Dimension(100,50));
        this.add(speedField);
    }
}
