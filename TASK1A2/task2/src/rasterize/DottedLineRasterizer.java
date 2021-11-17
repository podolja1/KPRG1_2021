package rasterize;

public class DottedLineRasterizer extends LineRasterizer {

    public DottedLineRasterizer(Raster raster) {
        super(raster);
    }

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
            for (int y = y1; y < y2; y+=3) {
                raster.setPixel(x1, y, color);
            }
        } else if (k < 1 && k > -1) {
            for (int x = x1; x <= x2; x+=3) {
                float y = (int) ((k * x) + q);
                raster.setPixel(x, (int) y, color);
            }
        } else {
            for (int y = y1; y <= y2; y+=3) {
                float x = (int) ((y - q) / k);
                raster.setPixel((int) x, y, color);
            }
        }
    }
}
