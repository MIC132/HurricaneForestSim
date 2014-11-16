import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Forest {
    Semaphore sem = new Semaphore(0);
    LinkedList<Tree> trees = new LinkedList<Tree>();

    public void run(){
        for(int i=0;i<10;i++){
            int okTrees = getOkTrees().size();
            //do stuff

            for(Tree t : trees){
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
}
