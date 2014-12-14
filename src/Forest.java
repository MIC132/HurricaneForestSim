import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Forest {
    Semaphore sem = new Semaphore(0);
    LinkedList<Tree> trees = new LinkedList<Tree>();
    ExecutorService exec;
    Random rng = new Random();

    double speedExt = 0; //Temporary solution

    public void run(){
        exec = Executors.newCachedThreadPool();
        for(Tree t : trees){
            t.forest = this;
            exec.execute(t);
        }

        int okTrees = getOkTrees().size();
        double speed = calcWindSpeed();

        for(Tree t : getOkTrees()){
            t.windSpeed = speed;
            t.sem.release();
        }

        try {
            sem.acquire(okTrees);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        exec.shutdownNow();
    }

    public LinkedList<Tree> getOkTrees(){
        LinkedList<Tree> result = new LinkedList<Tree>();
        for(Tree t : trees){
            if(t.state == Tree.State.OK) result.add(t);
        }
        return result;
    }

    double calcWindSpeed(){
        return speedExt;
    }

    Tree getClosestTree(Tree tree){
        double dist = 1000;
        LinkedList<Tree> temp = getOkTrees();
        temp.remove(tree);
        Tree output = tree;
        for (Tree t : temp){
            if(tree.getDistance(t) <= dist){
                output = t;
                dist = tree.getDistance(t);
            }
        }
        return output;
    }

    public void addRandomPines(int amount, int xLim, int yLim){
        for(int i=0;i<amount;i++){
            double coeff = rng.nextDouble();
            double trunkH = 2*coeff + 2;
            double trunkW = 50*coeff + 50 + rng.nextInt(10);
            double crownH = 15*coeff + 7 + rng.nextInt(5);
            double crownW = (3+rng.nextInt(2))*coeff + 3 ;
            trees.add(new ScotsPine(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }

    public void addRandomSpruces(int amount, int xLim, int yLim){
        for(int i=0;i<amount;i++){
            trees.add(new NorwaySpruce(rng.nextInt(xLim), rng.nextInt(yLim), rng.nextInt(3)+2, rng.nextInt(10)+25, rng.nextInt(5)+10, rng.nextInt(2)+2));
        }
    }

    public void addPines(int amount, int xLim, int yLim, double trunkH, double trunkW, double crownH, double crownW){
        for(int i=0;i<amount;i++){
            trees.add(new ScotsPine(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }

    public void addSpruces(int amount, int xLim, int yLim, double trunkH, double trunkW, double crownH, double crownW){
        for(int i=0;i<amount;i++){
            trees.add(new NorwaySpruce(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }
}
