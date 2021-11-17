package rasterize;

import model.Edge;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Clipper {


    public List<Point> clip(List<Point> polygon, List<Point> clipPolygon) {
        if(clipPolygon.size()<2){   //pro polygon který má méně než 2 body není důvod ho clipovat
            return polygon;         //navrátí se původní polygon
        }
        List<Point> newPolygon = new ArrayList<>(polygon);
        Point p1 = clipPolygon.get(clipPolygon.size()-1);   //uložení posledního bodu v seznamu

        for(Point p2 : clipPolygon)     //ekvivalent k seznamu úseček, p1 se spojí s p2, na konci cyklu (řadek 42) se do p1 uloží p2 a cykl jede znovu
        {
            Edge edge = new Edge(p1.x,p1.y,p2.x,p2.y);      //vytoření usečky z p1 a p2
            if(polygon.size()<2){       //na řádku 40,41 se do původního polygonu ukládá ořezany polygon, takže se může stát, že opět jeho velikost je >2, takže není důvod ořezávat
                return polygon;
            }
            List<Point> out = new ArrayList<>();
            out.clear();
            Point v1 = polygon.get(polygon.size()-1);
            for(Point v2 : polygon)     //na každé úsečce je potřeba projít všechny body, (4 možnosti jak usečka prochází resp. neprochází polygonem viz přednáška)
            {
                if (edge.isInside(v2)) {
                    if (!edge.isInside(v1))
                        out.add(edge.getIntersection(v1, v2));
                    out.add(v2);
                } else if (edge.isInside(v1)) {
                    out.add(edge.getIntersection(v1, v2));
                }
                v1 = v2;
            }
            newPolygon = out;   //do nového ořezaného polygonu se uloží nalezené průsečíky a na řádku 27 se out vyclearuje aby nedocházelo k duplicitám
            polygon = newPolygon; //do původního polygonu se uloží nový polygon s ořezanými body -> polygon = out proto 23. řádek kontrola jeho velikosti.
            p1 = p2;
        }
        return newPolygon;
    }
}

