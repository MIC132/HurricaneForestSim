public class NorwaySpruce extends Tree {
    public NorwaySpruce(int x, int y, double trunkH, double trunkW, double crownH, double crownW) {
        this.x = x;
        this.y = y;
        this.height = trunkH + crownH;
        this.trunkH = trunkH;
        this.trunkW = trunkW;
        this.crownH = crownH;
        this.crownW = crownW;
        this.rootD = height/2;
        this.dens = 430;                // kg/m^3
        this.rootM = 0.2 * Math.pow(crownH/2, 3) * dens/2;
        this.MOE = 6300;
        this.MOR = 30.6;
        this.dragCoeff = 0.35;
        this.soilToTreeRatio = 0.2;
    }

    double get_dist(double height){
        if(height >= 0 && height < trunkH) return trunkW;
        else if(height >= trunkH && height < this.height) return 2*(this.height-height)*(crownW/(2*crownH));
        else return 0;
    }

    @Override
    double getSurface(double height) {
        double wynik = 0;
        for(double x = height; x < height+1; x+= 0.01){
            wynik += 0.01*get_dist(x);
        }
        return wynik;
    }
}
