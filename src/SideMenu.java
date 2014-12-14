import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenu extends JPanel {
    final MainProgramWindow mainWindow;
    JButton runButton;
    JButton clearButton;
    JButton addRandom;
    JRadioButton pineButton;
    JRadioButton spruceButton;
    JTextField speedField = new JTextField();
    JTextField radnomAmount = new JTextField();
    JTextField trunkHField = new JTextField("2");
    JTextField trunkWField = new JTextField("70");
    JTextField crownHField = new JTextField("10");
    JTextField crownWField = new JTextField("3");

    public SideMenu(MainProgramWindow parent) {
        this.mainWindow = parent;
        this.setPreferredSize(new Dimension(100,600));
        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        pineButton = new JRadioButton("Scots Pine");
        pineButton.setMaximumSize(new Dimension(100,50));
        pineButton.setSelected(true);
        spruceButton = new JRadioButton("Norway Spruce");
        spruceButton.setMaximumSize(new Dimension(100,50));
        ButtonGroup treeButtons  = new ButtonGroup();
        treeButtons.add(pineButton);
        treeButtons.add(spruceButton);

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.sideMenu.runButton.setEnabled(false);
                mainWindow.run();
                mainWindow.sideMenu.runButton.setEnabled(true);
            }
        });
        runButton.setMaximumSize(new Dimension(100,50));

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.forest.trees.clear();
                mainWindow.displayPanel.repaint();
            }
        });
        clearButton.setMaximumSize(new Dimension(100,50));

        addRandom = new JButton("Add Random");
        addRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mainWindow.sideMenu.pineButton.isSelected()){
                    mainWindow.forest.addRandomPines(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 600);
                }else{
                    mainWindow.forest.addRandomSpruces(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 600);
                }

                mainWindow.displayPanel.repaint();
            }
        });
        addRandom.setMaximumSize(new Dimension(100, 50));


        JLabel speedLabel = new JLabel("Wind speed[m/s]");
        speedLabel.setForeground(Color.WHITE);
        speedField.setText("20");

        radnomAmount.setText("100");

        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setForeground(Color.WHITE);

        JLabel trunkHLabel = new JLabel("Trunk Height[m]");
        trunkHLabel.setForeground(Color.WHITE);
        JLabel trunkWLabel = new JLabel("Trunk Width[cm]");
        trunkWLabel.setForeground(Color.WHITE);
        JLabel crownHLabel = new JLabel("Crown Height[m]");
        crownHLabel.setForeground(Color.WHITE);
        JLabel crownWLabel = new JLabel("Crown Wifth[m]");
        crownWLabel.setForeground(Color.WHITE);

        JButton addButton = new JButton("Add");
        addButton.setMaximumSize(new Dimension(100,50));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double trunkH = Double.parseDouble(mainWindow.sideMenu.trunkHField.getText());
                double trunkW = Double.parseDouble(mainWindow.sideMenu.trunkWField.getText());
                double crownH = Double.parseDouble(mainWindow.sideMenu.crownHField.getText());
                double crownW = Double.parseDouble(mainWindow.sideMenu.crownWField.getText());
                if(mainWindow.sideMenu.pineButton.isSelected()){
                    mainWindow.forest.addPines(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 600,trunkH,trunkW,crownH,crownW);
                }else{
                    mainWindow.forest.addSpruces(Integer.parseInt(mainWindow.sideMenu.radnomAmount.getText()), 900, 600,trunkH,trunkW,crownH,crownW);
                }
                mainWindow.displayPanel.repaint();
            }
        });


        this.add(addRandom);
        this.add(amountLabel);
        this.add(radnomAmount);
        this.add(speedLabel);
        this.add(speedField);
        this.add(pineButton);
        this.add(spruceButton);
        this.add(trunkHLabel);
        this.add(trunkHField);
        this.add(trunkWLabel);
        this.add(trunkWField);
        this.add(crownHLabel);
        this.add(crownHField);
        this.add(crownWLabel);
        this.add(crownWField);
        this.add(addButton);
        this.add(runButton);
        this.add(clearButton);
    }
}
