import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenu extends JPanel {
    final MainProgramWindow mainWindow;
    JButton runButton;
    JButton addRandom;
    JRadioButton pineButton;
    JRadioButton spruceButton;
    JTextField speedField = new JTextField();
    JTextField radnomAmount = new JTextField();

    public SideMenu(MainProgramWindow parent) {
        this.mainWindow = parent;
        this.setPreferredSize(new Dimension(100,600));
        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        pineButton = new JRadioButton("Scots Pine");
        spruceButton = new JRadioButton("Norway Spruce");
        ButtonGroup treeButtons  = new ButtonGroup();
        treeButtons.add(pineButton);
        treeButtons.add(spruceButton);

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.run();
            }
        });
        runButton.setMaximumSize(new Dimension(100,50));

        addRandom = new JButton("Add");
        addRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainWindow.sideMenu.pineButton.isSelected()){
                    mainWindow.forest.addRandomPines(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 500);
                }else{
                    mainWindow.forest.addRandomSpruces(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 500);
                }

                mainWindow.displayPanel.repaint();
            }
        });
        addRandom.setMaximumSize(new Dimension(100, 50));


        JLabel speedLabel = new JLabel("Prędkośc wiatru");
        speedLabel.setForeground(Color.WHITE);
        speedField.setText("20");

        JLabel randomLabel = new JLabel("Losowe");
        randomLabel.setForeground(Color.WHITE);
        radnomAmount.setText("100");


        this.add(randomLabel);
        this.add(addRandom);
        this.add(radnomAmount);
        this.add(speedLabel);
        this.add(speedField);
        this.add(pineButton);
        this.add(spruceButton);
        this.add(runButton);
    }
}
