public class ScotsPine extends Tree {

    public ScotsPine(int x, int y,double trunkH, double trunkW, double crownH, double crownW) {
        this.x = x;
        this.y = y;
        this.height = trunkH + crownH;
        this.trunkH = trunkH;
        this.trunkW = trunkW;
        this.crownH = crownH;
        this.crownW = crownW;
        this.rootD = trunkH;
        this.dens = 550;                // kg/m^3
        this.rootM = 0.5 * Math.pow(crownW, 2)*rootD * dens;
        this.MOE = 10080; //7000;
        this.MOR = 39.1;
        this.dragCoeff = 0.29;
        this.soilToTreeRatio = 0.3;
    }

    double get_dist(double height) {
        if(height >= 0 && height < trunkH) return trunkW;
        if(height >= trunkH && height < trunkH+crownH/2) return (crownW*(height - trunkH))/crownH;
        if(height >= trunkH+crownH/2 && height < this.height) return crownW*(this.height - height)/crownH;
        return 0;
    }

    @Override
    double getSurface(double height) {
        double wynik = 0;
        for(double x = height; x < height+1; x+= 0.01){
            wynik += 0.02*get_dist(x);
        }
        return wynik;
    }

    @Override
    public String toString() {
        return "Scots Pine\n" + super.toString();
    }
}
