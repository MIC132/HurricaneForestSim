import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Forest {
    Semaphore sem = new Semaphore(0);
    LinkedList<Tree> trees = new LinkedList<Tree>();

    public void run(){
        for(int i=0;i<10;i++){
            int okTrees = getOkTrees().size();
            //do stuff

            double speed = calcWindSpeed();

            for(Tree t : getOkTrees()){
                t.windSpeed = speed;
                t.avgHeight = calcAvgHeight();
                t.avgDist = calcAvgCloseDist(t);
                t.sem.release();
            }

            try {
                sem.acquire(okTrees);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public LinkedList<Tree> getOkTrees(){
        LinkedList<Tree> result = new LinkedList<Tree>();
        for(Tree t : trees){
            if(t.state == Tree.State.OK) result.add(t);
        }
        return result;
    }

    double calcWindSpeed(){
        return 20;
    }

    double calcAvgHeight(){
        double total = 0;
        for(Tree t : getOkTrees()){
            total += t.height;
        }
        total /= getOkTrees().size();
        return total;
    }

    double calcAvgCloseDist(Tree tree){
        double total = 0;
        for(Tree t : getCloseTrees(tree, 100)){
            total += tree.getDistance(t);
        }
        total /= getCloseTrees(tree, 100).size();
        return total;
    }

    LinkedList<Tree> getCloseTrees(Tree tree, double dist){
        LinkedList<Tree> output = new LinkedList<Tree>();
        for (Tree t : getOkTrees()){
            if(tree.getDistance(t) <= dist){
                output.add(t);
            }
        }
        return output;
    }
}
