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
    double crownH;                  //from bottom of the crown gto top of the crown
    double trunkW;
    double crownW;
    double rootM;
    double rootD;

    Forest forest;

    volatile double windSpeed;
    volatile double avgHeight;
    volatile double avgDist;

    State state = State.OK;
    boolean shouldFinish = false;

    public enum State{OK, BROKEN, UPROOTED}

    Semaphore sem = new Semaphore(0);

    @Override
    public void run(){
        while(state == State.OK){
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if(shouldFinish) return;
            if(getTotalForce() >= getBreakingPoint()){
                state = State.BROKEN;
            }
            if(getTotalForce() >= getTopplePoint()){
                state = State.UPROOTED;
            }
            forest.sem.release();
        }
    }

    abstract double getSurface(double height);

    double getSurfaceCalibrated(double height){
        if(height < trunkH) return getSurface(height);
        if(windSpeed < 11){
            return getSurface(height)*0.8;
        }
        if(windSpeed >= 11 && windSpeed <= 20){
            return getSurface(height)*(0.044444*windSpeed-0.28889);
        }
            return getSurface(height)*0.4;
    }

    double getGravForce(double height){
        if(height < trunkH){
            return Math.pow(trunkW/200,2)*Math.PI*1*dens*gravity;
        }
        return (getSurfaceCalibrated(height) / 2.0) * (1.0 / 3.0) * Math.PI * 1 * dens * gravity;
    }
    double getWindForce(double height, double speed){
        return (0.5)*dragCoeff*airDens*Math.pow(speed,2)*getSurfaceCalibrated(height);
    }

    double getBreakingPoint(){
        return (Math.PI/32.0)*MOR*Math.pow(trunkW,3);

    }

    double getTopplePoint(){
        return (gravity*rootM)*(soilToTreeRatio*100);
    }

    double getBMax(double height){
        //return getFGust(1) * getFGap() * (getWindForce(height, windSpeed) + (getGravForce(height) * getDisplacement(height, windSpeed)));
        //System.out.print("Disp: " + getDisplacement(height, windSpeed) + " Height: " + height + "\n");
        //System.out.print("Wind: " + getWindForce(height, windSpeed) + " Grav: " + getGravForce(height) + "\n");
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
        /*
        if(height <= (trunkH+crownH/2))
            return (getWindForce(height, speed) * Math.pow((trunkH+crownH/2), 2) * this.height * (3 - ((trunkH+crownH/2) / this.height) - ((3 * (this.height - height)) / this.height)))
                / (6 * MOE * ((Math.PI * Math.pow(trunkW, 4)) / 64.0));
        return (getWindForce(height, speed) * Math.pow((trunkH+crownH/2), 3) * ((2 - ((3 * ((this.height - height) - ((trunkH+crownH/2) - this.height))) / (trunkH+crownH/2))) + (Math.pow(this.height - height - ((trunkH+crownH/2) - this.height), 3) / Math.pow((trunkH+crownH/2), 3))))
                / (6 * MOE * ((Math.PI * Math.pow(trunkW, 4)) / 64.0));
                */
        if(height == 0) return getWindForce(height, speed)/MOE;
        else return Math.sin(getWindForce(height, speed)/(MOE*(1 - height/this.height))) + getDisplacement(height-1, speed);
    }

    double getDistance(Tree tree){
        return Math.sqrt(Math.pow(this.x - tree.x, 2) + Math.pow(this.y - tree.y, 2) );
    }
}
