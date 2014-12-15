import java.util.concurrent.Semaphore;
//nope
public abstract class Tree implements Runnable{
    int x;                          //position
    int y;                          //position
    double MOR;                     //Modulus of Rupture
    double MOE;                     //Modulus of Elasticity
    double airDens = 1.226;         //Air density - preset
    double gravity = 9.81;          //Gravity acceleration - preset
    double dens;                    //wood density
    double dragCoeff;               //Drag coefficient of the tree. Found in literature
    double soilToTreeRatio;         //Ratio soil around the roots to roots themselves
    double height;                  //total height of tree [m]
    double trunkH;                  //from ground to crown [m]
    double crownH;                  //from bottom of the crown to top of the crown [m]
    double trunkW;                  //diameter of trunk [cm]
    double crownW;                  //diamtere of crown [m]
    double rootM;                   //mass of the roots [kg]
    double rootD;                   //depth of the roots [m]

    Forest forest;

    volatile double windSpeed;      //self explanatory

    State state = State.OK;         //state of the tree
    boolean shouldFinish = false;

    public enum State{OK, BROKEN, UPROOTED}

    Semaphore sem = new Semaphore(0);//Semafore starts with 0 permits, so it will wait initially

    //Main execution part of the tree
    @Override
    public void run(){
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(state == State.OK) {
            if (getTotalForce() >= getBreakingPoint()) {
                state = State.BROKEN;
            }
            if (getTotalForce() >= getTopplePoint()) {
                state = State.UPROOTED;
            }
        }
        forest.sem.release();
    }

    abstract double getSurface(double height);//Overriden in NorwaySpruce and ScotsPine

    //Returns side surface calibrated for changes caused by wind speed
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

    //Returns force applied by gravity to given height segment
    double getGravForce(double height){
        if(height < trunkH){
            return Math.pow(trunkW/200,2)*Math.PI*1*dens*gravity;
        }
        return (Math.pow(getSurface(height) / 2.0,2) * (1.0 / 10.0) * Math.PI * 1 * dens * gravity) + (Math.pow((1 - height/this.height)*trunkW/200,2)*Math.PI*1*dens*gravity);
    }

    //Returns force applied by wind to given height segment
    double getWindForce(double height, double speed){
        return dragCoeff*airDens*Math.pow(speed,2)*getSurfaceCalibrated(height);
    }

    //Returns force required to break tree
    double getBreakingPoint(){
        return (Math.PI/32.0)*MOR*Math.pow(trunkW,3);

    }

    //Returns force required to completely uproot the tree
    double getTopplePoint(){
        return (gravity*rootM)*soilToTreeRatio*100;
    }

    //Returns total froce applied to given height segment
    double getBMax(double height){
        return getWindForce(height, windSpeed)*height + getGravForce(height)*getDisplacement(height, windSpeed);
    }

    //Returns total turning force applied to whole tree
    double getTotalForce(){
        double total = 0;
        for(int i=0;i<height;i++){
            total += getBMax(i);

        }
        return total;
    }

    //Returns vertical displacement of given height segment. Uses prievous segment's displacement as base
    double getDisplacement(double height, double speed){
        if(height < trunkH) return 0;
        else return Math.sin(getWindForce(height, speed)/MOE) + getDisplacement(height-1, speed);
    }

    //Returns distance to given tree
    double getDistance(Tree tree){
        return Math.sqrt(Math.pow(this.x - tree.x, 2) + Math.pow(this.y - tree.y, 2) );
    }

    //Used for displaying info-tooltip
    @Override
    public String toString() {
        String output = "Trunk height[m]: " + trunkH + '\n';
        output += "Crown height[m]: " + crownH + '\n';
        output += "Total height[m]: " + height + '\n';
        output += "Trunk thickness[cm]: " + trunkW + '\n';
        output += "Crown width[m]: " + crownW + '\n';
        output += "State: ";
        switch (state) {
            case OK: output += "OK"; break;
            case BROKEN: output += "BROKEN"; break;
            case UPROOTED: output += "UPROOTED"; break;
        }
        return output;
    }
}