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
            trees.add(new ScotsPine(rng.nextInt(xLim), rng.nextInt(yLim), rng.nextInt(2)+2, rng.nextInt(45)+75, rng.nextInt(20)+10, rng.nextInt(3)+5));
        }
    }

    public void addRandomSpruces(int amount, int xLim, int yLim){
        for(int i=0;i<amount;i++){
            trees.add(new NorwaySpruce(rng.nextInt(xLim), rng.nextInt(yLim), rng.nextInt(3)+2, rng.nextInt(10)+25, rng.nextInt(5)+10, rng.nextInt(2)+2));
        }
    }
}
