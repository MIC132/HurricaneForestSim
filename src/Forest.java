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

    volatile double speedExt;

    //Executes the simulation
    public void run(){
        exec = Executors.newCachedThreadPool();
        for(Tree t : trees){
            t.forest = this;
            exec.execute(t); //Start the trees, but they'll wait on a semaphore
        }
        //We care only about non-broken and non-uprooted trees
        int okTrees = getStateTrees(Tree.State.OK).size();

        for(Tree t : getStateTrees(Tree.State.OK)){
            t.windSpeed = speedExt; //Inform about wind speed
            t.sem.release(); //Allow trees to work
        }

        try {
            sem.acquire(okTrees); //Wait for all relevant trees
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        exec.shutdownNow(); //Just in case
    }

    //List of all trees in given state
    public LinkedList<Tree> getStateTrees(Tree.State state){
        LinkedList<Tree> result = new LinkedList<Tree>();
        for(Tree t : trees){
            if(t.state == state) result.add(t);
        }
        return result;
    }

    //Not used currently, returns closest tree to given tree.
    Tree getClosestTree(Tree tree){
        double dist = -1;
        LinkedList<Tree> temp = getStateTrees(Tree.State.OK);
        temp.remove(tree);
        Tree output = tree;
        for (Tree t : temp){
            if(tree.getDistance(t) <= dist || dist==-1){
                output = t;
                dist = tree.getDistance(t);
            }
        }
        return output;
    }

    //Adds random Pines. Paramteres choosen to give realistic results.
    public void addRandomPines(int amount, int xLim, int yLim){
        for(int i=0;i<amount;i++){
            double coeff = rng.nextDouble(); //Basing all data on common coeff makes sure to avoid for example very thin trees with very big crown
            double trunkH = 2*coeff + 2;
            double trunkW = 50*coeff + 50 + rng.nextInt(10);
            double crownH = 15*coeff + 7 + rng.nextInt(5);
            double crownW = (3+rng.nextDouble()*2)*coeff + 2 ;
            trees.add(new ScotsPine(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }

    //Same as above, but for Spruces, and with different parameters
    public void addRandomSpruces(int amount, int xLim, int yLim){
        for(int i=0;i<amount;i++){
            double coeff = rng.nextDouble();
            double trunkH = 1*coeff + 1;
            double trunkW = 50*coeff + 50 + rng.nextInt(15);
            double crownH = 22*coeff + 20 + rng.nextInt(5);
            double crownW = (2+rng.nextDouble()*2)*coeff + 2 ;
            trees.add(new NorwaySpruce(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }

    //Adds given amount of pines, with set parameters
    public void addPines(int amount, int xLim, int yLim, double trunkH, double trunkW, double crownH, double crownW){
        for(int i=0;i<amount;i++){
            trees.add(new ScotsPine(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }

    //As above, just Spruces
    public void addSpruces(int amount, int xLim, int yLim, double trunkH, double trunkW, double crownH, double crownW){
        for(int i=0;i<amount;i++){
            trees.add(new NorwaySpruce(rng.nextInt(xLim), rng.nextInt(yLim), trunkH, trunkW, crownH, crownW));
        }
    }
}
