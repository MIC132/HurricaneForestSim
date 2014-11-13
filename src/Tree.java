public abstract class Tree extends Agent{
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

    double getFGust(double s, double h, double x){
        double GMean = Math.pow(((0.68 * (s / h)) - 0.0385) + (((-0.68 * (s / h)) + 0.4875) * ((1.7239 * (s / h)) + 0.0316)),x/h);
        double GMax = Math.pow(((2.7913 * (s / h)) - 0.061) + (((-1.273 * (s / h)) + 9.9701) * ((1.1127 * (s / h)) + 0.0311)),x/h);

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

    double getBMax(double height, double speed){
        return getFGust(1, 1, 1) * getFGap() * (getWindForce(height, speed) + (getGravForce(height) * getDisplacement(height)));
    }

    double getDisplacement(double height){
        return 1;
        //TODO: implement the equation 2.10/9
    }
}
