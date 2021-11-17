package fill;

import model.Edge;
import model.Polygon;
import rasterize.PolygonRasterizer;
import rasterize.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine implements Filler {

    private final Raster raster;
    private final List<Point> points;
    private final int fillColor;
    private final int borderColor;

    public ScanLine(Raster raster, List<Point> points, int fillColor, int borderColor) {
        this.raster = raster;
        this.points = points;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public void fill() {
        List<Edge> edges = new ArrayList<>();


        int minY = points.get(0).y;
        int maxY = minY;

        for(int i = 1; i<= points.size();++i)   //procházení celého seznamu bodů a z něj vytvoření úseček řádek 42
        {
            int x1 = points.get(i-1).x;
            int y1 = points.get(i-1).y;
            int x2 = points.get((i)%points.size()).x;
            int y2 = points.get((i)%points.size()).y;

            Edge edge = new Edge(x1,y1,x2,y2);

            if(!edge.isHorizontal()){   //pokud úsečka není horizontální tak ji můžeme orientovat tak, aby y1 nebylo větší než y2 (class Edge řádek 21)
                edge.orientate();
                if(edge.y1 < minY) minY = edge.y1;
            }if(edge.y2>maxY) maxY = edge.y2;
            edges.add(edge);    //nalezneme maximum a minimum z hodnot Y a přidáme hranu do seznamu useček
        }



        for (int y = minY; y <= maxY; y++) {    // pro všechny Y od Ymin do Ymax zjišťujeme jestli hrana má průsečíky, pokud ano přidáme vypočítanou hodnotu X do seznamu
            List<Integer> intersections = new ArrayList<>();
            for(Edge edge:edges){
                if(edge.hasIntersection(y)){
                    intersections.add(edge.getIntersection(y));
                }
            }
            Collections.sort(intersections);    //setřídení seznamu


            for(int i = 0; i < intersections.size()-1; i+=2){   //spojujeme každé 2 body, aby nedošlo k novým spojením tzn (usečka mezi 1. a 2., 3. a 4. apod.)
                int x1 =intersections.get(i);
                int x2 = intersections.get(i+1);

                for(int x = x1; x<=x2;++x){
                    raster.setPixel(x,y,fillColor);
                }
            }
        }

        PolygonRasterizer polygonRasterizer = new PolygonRasterizer(raster);    //obtažení hranice
        polygonRasterizer.rasterize(new Polygon(points),borderColor);
    }
}
