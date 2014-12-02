import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenu extends JPanel {
    final MainProgramWindow mainWindow;
    public SideMenu(MainProgramWindow parent) {
        this.mainWindow = parent;
        this.setPreferredSize(new Dimension(100,500));
        this.setBackground(Color.BLACK);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.run();
            }
        });
        runButton.setMinimumSize(new Dimension(100, 50));
        runButton.setMaximumSize(new Dimension(100,50));
        this.add(runButton);
    }
}
