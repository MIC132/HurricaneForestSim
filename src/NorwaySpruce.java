public class NorwaySpruce extends Tree {

    public NorwaySpruce(int x, int y, double trunkH, double trunkW, double crownH, double crownW) {
        this.x = x;
        this.y = y;
        this.height = trunkH + crownH;
        this.trunkH = trunkH;
        this.trunkW = trunkW;
        this.crownH = crownH;
        this.crownW = crownW;
        this.rootD = trunkH;
        this.dens = 405;                // kg/m^3
        this.rootM = 0.3 * Math.pow(crownW*2, 2)*rootD * dens;
        this.MOE = 9700; //6300;
        this.MOR = 30.6;
        this.dragCoeff = 0.29;
        this.soilToTreeRatio = 0.3;
    }

    //Calculates 'radius' of tree on given height
    double get_dist(double height){
        if(height >= 0 && height < trunkH) return trunkW;
        else if(height >= trunkH && height < this.height)
            return 2*(this.height-height)*(crownW/(crownH));
        else return 0;
    }

    //Calculates surface of the tree, using above 'radius'
    @Override
    double getSurface(double height) {
        double wynik = 0;
        for(double x = height; x < height+1; x+= 0.01){
            wynik += 0.01*get_dist(x);
        }
        return wynik;
    }

    @Override
    public String toString() {
        return "Norway Spruce\n" + super.toString();
    }
}
