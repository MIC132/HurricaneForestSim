import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel{
    Forest forest;

    public DisplayPanel(Forest forest) {
        this.forest = forest;
        this.setPreferredSize(new Dimension(400,500));
        this.setBackground(Color.BLUE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
