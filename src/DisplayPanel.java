import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DisplayPanel extends JPanel{
    final MainProgramWindow mainWindow;
    Forest forest;

    public DisplayPanel(MainProgramWindow parent, Forest forest) {
        this.mainWindow = parent;
        this.forest = forest;

        final JPopupMenu popup = new JPopupMenu("Tree data");
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                popup.setVisible(false);
                popup.removeAll();

                int x = e.getX();
                int y = e.getY();

                Tree currentTree = null;
                for(Tree t : mainWindow.forest.trees){
                    if(t.x > x-2 && t.x < x+2 && t.y > y-2 && t.y < y+2){
                        currentTree = t;
                        break;
                    }
                }
                if(currentTree != null){
                    JTextArea textArea = new JTextArea(currentTree.toString());
                    textArea.setEditable(false);
                    popup.add(textArea);
                    popup.show(mainWindow.displayPanel,x,y);
                    popup.setVisible(true);
                }

            }
        });

        this.setPreferredSize(new Dimension(900,600));
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
        g.fillOval(t.x-2,t.y-2,5,5);
    }



}
