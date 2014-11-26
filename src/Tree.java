import java.util.concurrent.Semaphore;
//nope
public abstract class Tree extends Agent{
    double x;
    double y;
    double MOR;
    double MOE;
    double airDens = 1.226;
    double gravity = 9.81;
    double dens;
    double dragCoeff;
    double soilToTreeRatio;
    double height;
    double trunkH;
    double crownH;
    double trunkW;
    double crownW;
    double rootM;
    double rootD;

    Forest forest;

    volatile double windSpeed;
    volatile double avgHeight;
    volatile double avgDist;

    State state = State.OK;

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
            return Math.pow(trunkW/2,2)*Math.PI*1*dens*gravity;
        }
        return (getSurfaceCalibrated(height) / 2.0) * (1.0 / 3.0) * Math.PI * 1 * dens * gravity;
    }
    double getWindForce(double height, double speed){
        return (0.5)*dragCoeff*airDens*Math.pow(speed,2)*getSurfaceCalibrated(height);
    }

    double getFGust(double x){
        double GMean = Math.pow(((0.68 * (avgDist / avgHeight)) - 0.0385) + (((-0.68 * (avgDist / avgHeight)) + 0.4875) * ((1.7239 * (avgDist / avgHeight)) + 0.0316)),x/avgHeight);
        double GMax = Math.pow(((2.7913 * (avgDist / avgHeight)) - 0.061) + (((-1.273 * (avgDist / avgHeight)) + 9.9701) * ((1.1127 * (avgDist / avgHeight)) + 0.0311)),x/avgHeight);
        //return GMax/GMean;
        return 1;
    }

    double getFGap(){
        return 1;
    }

    double getBreakingPoint(){
        return (Math.PI/32.0)*MOR*Math.pow(trunkW,3);
    }

    double getTopplePoint(){
        return (gravity*rootM*rootD)/soilToTreeRatio;
    }

    double getBMax(double height){
        return getFGust(1) * getFGap() * (getWindForce(height, windSpeed) + (getGravForce(height) * getDisplacement(height, windSpeed)));
    }

    double getTotalForce(){
        double total = 0;
        for(int i=0;i<height;i++){
            total += getBMax(i);
        }
        return total/1000000;
    }

    double getDisplacement(double height, double speed){
        if(height <= (trunkH+crownH/2)) return (getWindForce(height, speed) * Math.pow((trunkH+crownH/2), 2) * this.height * (3 - ((trunkH+crownH/2) / this.height) - ((3 * (this.height - height)) / this.height)))
                / (6 * MOE * ((Math.PI * Math.pow(trunkW, 4)) / 64.0));
        return (getWindForce(height, speed) * Math.pow((trunkH+crownH/2), 3) * ((2 - ((3 * ((this.height - height) - ((trunkH+crownH/2) - this.height))) / (trunkH+crownH/2))) + (Math.pow(this.height - height - ((trunkH+crownH/2) - this.height), 3) / Math.pow((trunkH+crownH/2), 3))))
                / (6 * MOE * ((Math.PI * Math.pow(trunkW, 4)) / 64.0));
    }

    double getDistance(Tree tree){
        return Math.sqrt(Math.pow(this.x - tree.x, 2) + Math.pow(this.y - tree.y, 2) );
    }
}
