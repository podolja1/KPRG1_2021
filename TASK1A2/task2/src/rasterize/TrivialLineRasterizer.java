package rasterize;

public class TrivialLineRasterizer extends LineRasterizer {

    public TrivialLineRasterizer (Raster raster) {
        super(raster);
    }

    // Triviální algoritmus vykreslování úsečky
    // Je nutné dávat pozor na počáteční a koncový bod úsečky (jestli je x1<x2 a pod.) a sklon úsečky, t.j. směrnici k
    // Jinými slovy je nutné určit řídící osu (x nebo y) dle hodnoty směrnice (k), t.j. k<1 => x, k<1 => y
    // Výhody: použitelný i pro složitejší křivky
    // Nevýhody: neefektivní násobení a sčítání v plovoucí řádové čárce

    @Override
    public void rasterize(int x1, int y1, int x2, int y2, int color) {
        float k = (float) (y2 - y1) / (x2 - x1);
        float q = y1 - k * x1;
        int temp;

        if (x2 < x1) {
            temp = x2;
            x2 = x1;
            x1 = temp;
        }
        if (y2 < y1) {
            temp = y2;
            y2 = y1;
            y1 = temp;
        }

        if (x1 == x2) {
            for (int y = y1; y < y2; ++y) {
                raster.setPixel(x1, y, color);
            }
        } else if (k < 1 && k > -1) {
            for (int x = x1; x <= x2; x++) {
                float y = (int) ((k * x) + q);
                raster.setPixel(x, (int) y, color);
            }
        } else {
            for (int y = y1; y <= y2; y++) {
                float x = (int) ((y - q) / k);
                raster.setPixel((int) x, y, color);
            }
        }
    }
}
