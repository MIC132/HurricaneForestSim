import java.util.concurrent.Semaphore;

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
            if(getTotalForce() > getTopplePoint()){
                state = State.BROKEN;
            }else if(getTotalForce()>getBreakingPoint()){
                state = State.UPROOTED;
            }
            forest.sem.release();
        }
    }

    abstract double getSurface(double height);

    double getGravForce(double height){
        if(height < trunkH){
            return Math.pow(trunkW/2,2)*Math.PI*1*dens*gravity;
        }
        return (getSurface(height) / 2) * (1 / 3) * Math.PI * 1 * dens * gravity;
    }
    double getWindForce(double height, double speed){
        return (1/2)*dragCoeff*airDens*Math.pow(speed,2)*getSurface(height);
    }

    double getFGust(double x){
        double GMean = Math.pow(((0.68 * (avgDist / avgHeight)) - 0.0385) + (((-0.68 * (avgDist / avgHeight)) + 0.4875) * ((1.7239 * (avgDist / avgHeight)) + 0.0316)),x/avgHeight);
        double GMax = Math.pow(((2.7913 * (avgDist / avgHeight)) - 0.061) + (((-1.273 * (avgDist / avgHeight)) + 9.9701) * ((1.1127 * (avgDist / avgHeight)) + 0.0311)),x/avgHeight);
        return GMax/GMean;
    }

    double getFGap(){
        return 1;
    }

    double getBreakingPoint(){
        return (Math.PI/32)*MOR*Math.pow(trunkW,3);
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
        return total;
    }

    double getDisplacement(double height, double speed){
        if(height <= crownH) return (getWindForce(height, speed) * Math.pow(crownH, 2) * this.height * (3 - (crownH / this.height) - ((3 * (this.height - height)) / this.height)))
                / ((6 * MOE * Math.PI * Math.pow(getSurface(1.3), 4)) / 64);
        return (getWindForce(height, speed) * Math.pow(crownH, 3) * ((2 - ((3 * ((this.height - height) - (crownH - this.height))) / crownH)) + (Math.pow(this.height - height - (crownH - this.height), 3) / Math.pow(crownH, 3))))
                / ((6 * MOE * Math.PI * Math.pow(getSurface(1.3), 4)) / 64);
    }

    double getDistance(Tree tree){
        return Math.sqrt(Math.pow(this.x - tree.x, 2) + Math.pow(this.y - tree.y, 2) );
    }
}
