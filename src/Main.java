import java.util.Random;

public class Main {
    public static void main(String[] args){
        Forest forest = new Forest();
        Random rng = new Random();
        for(int i=0;i<10;i++){
            forest.trees.add(new ScotsPine(rng.nextInt(100), rng.nextInt(100),5, 1.5,15,10));
        }
        for(Tree t: forest.trees){
            t.forest = forest;
        }
        forest.run();
        for(Tree t: forest.trees){
            //System.out.println(t.getDisplacement(1,10));
            System.out.print(t.getTotalForce());
            System.out.print(' ');
            System.out.print(t.getBreakingPoint());
            System.out.print(' ');
            System.out.print(t.getTopplePoint());
            System.out.println(t.state);
        }
    }
}
