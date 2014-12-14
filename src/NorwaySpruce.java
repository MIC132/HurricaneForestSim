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
        this.dens = 550;                // kg/m^3
        this.rootM = 0.1 * Math.pow(crownW, 2)*rootD/2 * dens;
        this.MOE = 6300;
        this.MOR = 30.6;
        this.dragCoeff = 0.29;
        this.soilToTreeRatio = 0.3;
    }

    @Override
    double getSurface(double height) {
        if (height > this.height || height < 0) return 0;
        else if (height + 1 <= trunkH) return trunkW * 1/100;            //trunkW * 1m
        else if (height < trunkH && height + 1 > trunkH)
            return (trunkH - height) * trunkW/100 + ((Math.pow(height + 1 - trunkH, 2) / crownH) * Math.sqrt(Math.pow(height + 1 - trunkH, 2) + Math.pow(crownW / 2, 2)) * Math.sin(Math.atan(crownW / (2*crownH))));
        else if (height >= trunkH && height + 1 < height)
            return ((Math.pow(height + 1 - trunkH, 2) / crownH) * Math.sqrt(Math.pow(height + 1 - trunkH, 2) + Math.pow(crownW / 2, 2)) * Math.sin(Math.atan(crownW / (2*crownH))))
                    - ((Math.pow(height - trunkH, 2) / crownH) * Math.sqrt(Math.pow(height - trunkH, 2) + Math.pow(crownW / 2, 2)) * Math.sin(Math.atan(crownW / (2*crownH))));
        else if(height >= trunkH && height+1 >this.height)
            return ((Math.pow(this.height-height, 2) / crownH) * Math.sqrt(Math.pow(this.height-height, 2) + Math.pow(crownW / 2, 2)) * Math.sin(Math.atan(crownW / (2*crownH))));
        else return 0;
    }
}
