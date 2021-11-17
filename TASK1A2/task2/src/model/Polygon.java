package model;

import java.awt.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {

    private final List<Point> points;
    private final int color;

    public Polygon() {
        this(new ArrayList<>());
    }

    public Polygon(List<Point> points) {
        this(points, Color.MAGENTA.getRGB());
    }

    public Polygon(List<Point> points, int color) {
        this.points = points;
        this.color = color;
    }

    public void addPoints(Point... pointsToAdd) {
        points.addAll(Arrays.asList(pointsToAdd));
    }

    public void addPoints(List<Point> pointsToAdd) {
        points.addAll(pointsToAdd);
    }

    public Point getPoint(int index)
    {
        return points.get(index);
    }

    public void clear(){points.clear();}

    public int getSize()
    {
        return points.size();
    }

    public boolean isEmpty()
    {
        return points.isEmpty();
    }

    public List<Point> getPoints()
    {
        return points;
    }
}
