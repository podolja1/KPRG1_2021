package control;

import fill.ScanLine;
import fill.SeedFiller;
import model.Polygon;
import model.Triangle;
import rasterize.*;
import view.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Controller2D implements Controller {

    private final Panel panel;
    private final Raster raster;

    private LineRasterizer trivialLineRasterizer;
    private LineRasterizer dottedLineRasterizer;
    private SeedFiller seedFiller;
    private ScanLine scanLine;
    private Polygon polygon;
    private Polygon clipPolygon;
    private Polygon clippedPolygon;
    private PolygonRasterizer polygonRasterizer;
    private Triangle triangle;
    private Clipper clipper;

    private boolean Tpoint=false;

    private int x,y;

    public Controller2D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRaster();
        initObjects(panel.getRaster());
        initListeners(panel);
    }

    private void initObjects(Raster raster) {
        trivialLineRasterizer = new TrivialLineRasterizer(raster);
        dottedLineRasterizer = new DottedLineRasterizer(raster);
        polygonRasterizer = new PolygonRasterizer(raster);

        polygon = new Polygon();
        clipPolygon = new Polygon();
        clippedPolygon = new Polygon();

        clipper = new Clipper();

        seedFiller = new SeedFiller(raster);
        scanLine = new ScanLine(raster,clippedPolygon.getPoints(),0xffff00,0xffffff);
    }

    @Override
    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isControlDown()) return;

                if (e.isShiftDown()) {

                } else if (SwingUtilities.isLeftMouseButton(e)) {

                    if(polygon.isEmpty())
                    {
                        polygon.addPoints(e.getPoint());
                    }


                } else if (SwingUtilities.isMiddleMouseButton(e)) {

                } else if (SwingUtilities.isRightMouseButton(e)) {
                    if(clipPolygon.isEmpty())
                    {
                        clipPolygon.addPoints(e.getPoint());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    raster.clear();

                    if (!Tpoint) {
                        polygon.addPoints(e.getPoint());
                        update();
                    } else if (Tpoint) {
                        if (polygon.getSize() < 2) {
                            polygon.addPoints(e.getPoint());
                            if (polygon.getSize() == 2) {
                                triangle = new Triangle(polygon);
                                polygon.addPoints(triangle.calculThirdPoint());
                                update();
                            }
                        }
                    }
                } else if (SwingUtilities.isRightMouseButton(e)){
                    raster.clear();
                    if(!Tpoint)
                    {
                        clipPolygon.addPoints(e.getPoint());
                        clippedPolygon = new Polygon(clipper.clip(polygon.getPoints(),clipPolygon.getPoints()));

                        update();
                    }
                }
                if(clippedPolygon.getSize()>2)
                {
                    scanLine= new ScanLine(raster,clippedPolygon.getPoints(),0x00fff0,0xffffff);
                    scanLine.fill();
                }
            }



            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.isControlDown()) return;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    raster.clear();
                    dottedLineRasterizer.rasterize(polygon.getPoint(0).x, polygon.getPoint(0).y, e.getX(), e.getY(), 0xffffff);
                    dottedLineRasterizer.rasterize(polygon.getPoint(polygon.getSize()-1).x, polygon.getPoint(polygon.getSize()-1).y,
                            e.getX(),e.getY(),0xffffff);

                } else if (SwingUtilities.isRightMouseButton(e)) {

                } else if (SwingUtilities.isMiddleMouseButton(e)) {

                }
                update();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // na klávesu C vymazat plátno
                if (e.getKeyCode() == KeyEvent.VK_C) {

                    raster.clear();
                    panel.repaint();
                    polygon.clear();
                    clipPolygon.clear();
                    clippedPolygon.clear();

                }else if(e.getKeyCode()==KeyEvent.VK_T)
                {
                    Tpoint=!Tpoint;
                }
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
                initObjects(panel.getRaster());
            }
        });
    }

    private void update() {

        polygonRasterizer.rasterize(polygon,0xff00ff);
        polygonRasterizer.rasterize(clipPolygon,0xffff00);
        polygonRasterizer.rasterize(clippedPolygon,0xffffff);
        panel.repaint();
    }

    private void hardClear() {
        panel.clear();
    }
}
