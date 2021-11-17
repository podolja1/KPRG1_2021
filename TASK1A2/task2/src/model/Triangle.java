package model;

import java.awt.*;
import java.awt.Point;

public class Triangle {

    Polygon polygon;

    public Triangle(Polygon polygon)
    {
        this.polygon = polygon;
    }

    public Point calculThirdPoint()     //vypočítání 3. bodu, který leží na thaletově kružici
    {
        Point thirdPoint;
        int x1,x2,y1,y2;
        x1 = polygon.getPoint(0).x;
        x2 = polygon.getPoint(1).x;
        y1 = polygon.getPoint(0).y;
        y2 = polygon.getPoint(1).y;

        int SX = (x1+x2)/2;
        int SY = (y1+y2)/2;

        int xDifference = x2-x1;
        int yDifference = y2-y1;

        int r = (int)Math.sqrt((xDifference*xDifference)+(yDifference*yDifference))/2; //vzorec pro poloměr

        if(yDifference<40)
        {
            thirdPoint = new Point(SX,SY+r);
        }else thirdPoint = new Point(SX+r,SY);      //orientace 3. bodu podle rozdílu y-souřadnic bodů

        return thirdPoint;
    }
}
