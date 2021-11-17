package model;

import java.awt.*;

public class Edge {

    public int x1, y1, x2, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal() {
        return y1 == y2;

    }

    public void orientate() {   //pokud je y2 menší než y1 tak se hodnoty y a x prohodí tzv. orientace usečky
        if (y1 > y2) {
            int temp = y1;
            y1 = y2;
            y2 = temp;

            temp = x1;
            x1 = x2;
            x2 = temp;
        }
    }

    public boolean hasIntersection(int y) {     //pokud se y-hodnota nějakého bodu nachází mezi krajními body usečky (tzn. y1 a y2) tak má nejspíše průsečík s touto hranou

        return (y>=y1 && y< y2);
    }

    public int getIntersection(int y) {     //vypočítání x-souřadnice průsečíku s hranou
        float k = (float)(x2-x1)/(y2-y1);
        float q = x1 - k*y1;
        int x = (int)(k*y+q);
        return x;
    }

    public boolean isInside(Point p) {      //výpočet pro zjištění normálového vektoru a vektoru přímky pro zjištění zda se bod nachází vně nebo vevnitř polygonu
        Point t = new Point(x2 - x1, y2 - y1);  //Class clipper řádek 31-35

        Point n = new Point(t.y, -t.x);
//      Point n = new Point(-t.y, t.x);

        Point v = new Point(p.x - x1, p.y - y1);

        return v.x * n.x + v.y * n.y < 0;
    }

    public Point getIntersection(Point p3, Point p4) {  //metoda, která zjistí na jakých souřadnicích se protnout 2 přímky

        float interX = (float)((p3.x * p4.y - p4.x * p3.y) * (x1 - x2) - (x1*y2 - x2*y1) * (p3.x - p4.x))/((p3.x-p4.x) * (y1-y2) - (p3.y - p4.y) * (x1-x2));
        float interY = (float)((p3.x * p4.y - p4.x * p3.y) * (y1 - y2) - (x1*y2 - x2*y1) * (p3.y - p4.y))/((p3.x-p4.x) * (y1-y2) - (p3.y - p4.y) * (x1-x2));

        return new Point((int)interX, (int)interY);
    }
}
