import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel{
    final MainProgramWindow mainWindow;
    Forest forest;

    public DisplayPanel(MainProgramWindow parent, Forest forest) {
        this.mainWindow = parent;
        this.forest = forest;
        this.setPreferredSize(new Dimension(400,500));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(Tree t:forest.trees){
            paintTree(t,g);
        }
    }

    public void paintTree(Tree t, Graphics g){
        if(t.state == Tree.State.OK){
            g.setColor(Color.GREEN);
        }else if(t.state == Tree.State.BROKEN){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.BLACK);
        }
        g.fillOval(t.x+2,t.y+2,5,5);
    }
}
