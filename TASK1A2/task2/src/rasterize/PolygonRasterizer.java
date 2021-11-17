package rasterize;

import model.Line;
import model.Polygon;

import java.awt.*;
import java.util.List;

public class PolygonRasterizer {

    LineRasterizer lineRasterizer;

    public PolygonRasterizer(Raster raster)
    {
        lineRasterizer = new TrivialLineRasterizer(raster);
    }

    public void rasterize(Polygon polygon,int color)
    {
        for(int i = 1; i <= polygon.getSize();++i)
        {
            Point start = polygon.getPoint(i-1);
            Point end = polygon.getPoint(i % polygon.getSize());

            lineRasterizer.rasterize(start,end,color);
        }
    }
}
