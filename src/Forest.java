import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Forest {
    Semaphore sem = new Semaphore(0);
    LinkedList<Tree> trees = new LinkedList<Tree>();
    ExecutorService exec = Executors.newCachedThreadPool();

    public void run(){
        for(Tree t : trees){
            exec.execute(t);
        }
        for(int i=0;i<2;i++){
            int okTrees = getOkTrees().size();
            double speed = calcWindSpeed();

            for(Tree t : getOkTrees()){
                t.windSpeed = speed;
                t.avgHeight = calcAvgHeight();
                t.avgDist = calcAvgCloseDist();
                t.sem.release();
            }

            try {
                sem.acquire(okTrees);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        exec.shutdown();
    }

    public LinkedList<Tree> getOkTrees(){
        LinkedList<Tree> result = new LinkedList<Tree>();
        for(Tree t : trees){
            if(t.state == Tree.State.OK) result.add(t);
        }
        return result;
    }

    double calcWindSpeed(){
        return 80;
    }

    double calcAvgHeight(){
        double total = 0;
        for(Tree t : getOkTrees()){
            total += t.height;
        }
        total /= getOkTrees().size();
        return total;
    }

    double calcAvgCloseDist(){
        double total = 0;
        for(Tree t : getOkTrees()){
            total += t.getDistance(getClosestTree(t));
        }
        total /= getOkTrees().size();
        return total;
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
}
