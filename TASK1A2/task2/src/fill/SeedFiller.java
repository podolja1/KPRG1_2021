package fill;

import rasterize.Raster;

import java.awt.*;

public class SeedFiller implements Filler {

    private final Raster raster;
    private int seedX, seedY;
    private int backgroundColor;
    private int fillColor;

    public SeedFiller(Raster raster) {
        this.raster = raster;
    }

    public void setSeed(Point seed) {   //setSeed pro nastavení počátku pro záplavu
        seedX = seed.x;
        seedY = seed.y;
        backgroundColor = raster.getPixel(seedX, seedY);    //zjištění barvy pozadí pro začátek záplavového algoritmu
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public void fill() {
        seed(seedX, seedY);

    }

    private void seed(int x, int y) {
        // podminka na velikost platna
        if (backgroundColor == raster.getPixel(x,y)) {  //pokud je barva na daném místě stejná jako barva počátku před obarvením tak se podmínka splní
            raster.setPixel(x, y, fillColor);
            seed(x + 1, y);   // vpravo                rekurze do 4 směrů -> metoda volá sama sebe s jinými parametry
            seed(x - 1,y);    // vlevo
            seed(x, y + 1);   // dolu
            seed(x, y - 1);   // nahoru
        }
    }
}
