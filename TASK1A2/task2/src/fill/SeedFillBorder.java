package fill;


import rasterize.Raster;

import java.awt.*;

public class SeedFillBorder implements Filler { //funguje stejně jako seedfiller, ale místo background color se ptáme na barvu hranice řádek 38, + přibyla nová metoda setBorderColor

    private final Raster raster;
    private int seedX, seedY;
    private int borderColor;
    private int fillColor;

    public SeedFillBorder(Raster raster) {
        this.raster = raster;
    }

    public void setSeed(Point seed) {
        seedX = seed.x;
        seedY = seed.y;
        borderColor = 0x0ff0f0;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }
    public void setBorderColor(int borderColor){this.borderColor =borderColor;}

    @Override
    public void fill() {
        seed(seedX, seedY);

    }

    private void seed(int x, int y) {
        // podminka na velikost platna
        if (borderColor != raster.getPixel(x,y)) {
            raster.setPixel(x, y, fillColor);
            seed(x + 1, y);   // right
            seed(x - 1,y);    // left
            seed(x, y + 1);   // down
            seed(x, y - 1);   // up
        }
    }
}
