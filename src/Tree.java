import java.util.concurrent.Semaphore;
//nope
public abstract class Tree extends Agent{
    int x;
    int y;
    double MOR;
    double MOE;
    double airDens = 1.226;
    double gravity = 9.81;
    double dens;
    double dragCoeff;
    double soilToTreeRatio;
    double height;
    double trunkH;
    double crownH;                  //from bottom of the crown to top of the crown
    double trunkW;
    double crownW;
    double rootM;
    double rootD;

    Forest forest;

    volatile double windSpeed;

    State state = State.OK;
    boolean shouldFinish = false;

    public enum State{OK, BROKEN, UPROOTED}

    Semaphore sem = new Semaphore(0);

    @Override
    public void run(){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(state == State.OK){
            if(getTotalForce() >= getBreakingPoint()){
                state = State.BROKEN;
            }
            if(getTotalForce() >= getTopplePoint()){
                state = State.UPROOTED;
            }
        }

        System.out.print("F: " + getTotalForce() + " BP: " + getBreakingPoint() + " TP: " + getTopplePoint() + "\n");

        forest.sem.release();
    }

    abstract double getSurface(double height);

    double getSurfaceCalibrated(double height){
        if(height < trunkH) return getSurface(height);
        if(windSpeed < 11){
            return getSurface(height)*0.8;
        }
        if(windSpeed >= 11 && windSpeed <= 20){
            return getSurface(height)*(1-(0.044444*windSpeed-0.28889));
        }
            return getSurface(height)*0.4;
    }

    double getGravForce(double height){
        if(height < trunkH){
            return Math.pow(trunkW/200,2)*Math.PI*1*dens*gravity;
        }
        return (Math.pow(getSurface(height) / 2.0,2) * (1.0 / 3.0) * Math.PI * 1 * dens * gravity) + (Math.pow((1 - height/this.height)*trunkW/200,2)*Math.PI*1*dens*gravity);
    }
    double getWindForce(double height, double speed){
        return dragCoeff*airDens*Math.pow(speed,2)*getSurfaceCalibrated(height);
    }

    double getBreakingPoint(){
        return (Math.PI/32.0)*MOR*Math.pow(trunkW,3);

    }

    double getTopplePoint(){
        return (gravity*rootM)*soilToTreeRatio*100;
    }

    double getBMax(double height){
        return getWindForce(height, windSpeed)*height + getGravForce(height)*getDisplacement(height, windSpeed);
    }

    double getTotalForce(){
        double total = 0;
        for(int i=0;i<height;i++){
            total += getBMax(i);

        }
        return total;
    }

    double getDisplacement(double height, double speed){
        if(height == 0) return getWindForce(height, speed)/MOE;
        else return Math.sin(getWindForce(height, speed)/(MOE*(1 - height/this.height))) + getDisplacement(height-1, speed);
    }

    double getDistance(Tree tree){
        return Math.sqrt(Math.pow(this.x - tree.x, 2) + Math.pow(this.y - tree.y, 2) );
    }

    @Override
    public String toString() {
        String output = "Trunk height[m]: " + trunkH + '\n';
        output += "Crown height[m]: " + crownH + '\n';
        output += "Total height[m]: " + (trunkH+crownH) + '\n';
        output += "Trunk thickness[cm]: " + trunkW + '\n';
        output += "Crown width[m]: " + crownW;
        return output;
    }
}