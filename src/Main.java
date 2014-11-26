import java.util.Random;

public class Main {
    public static void main(String[] args){
        Forest forest = new Forest();
        Random rng = new Random();
        for(int i=0;i<10;i++){
            forest.trees.add(new ScotsPine(rng.nextInt(100), rng.nextInt(100),2, 30,10,3));
        }
        for(Tree t: forest.trees){
            t.forest = forest;
        }
        forest.run();
        for(Tree t: forest.trees){
            //System.out.println(t.getDisplacement(1,10));
            System.out.print("Tf: " + t.getTotalForce());
            System.out.print(' ');
            System.out.print("Bp: " + t.getBreakingPoint());
            System.out.print(' ');
            System.out.print("Tp: " + t.getTopplePoint());
            System.out.println(" State: " + t.state);
        }
    }
}
