package main.editor.vec;

import game.AbstractGame;
import game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import main.editor.io.Vec2dfColoredIO;
import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;
import org.geom.vector.vec3d.Vec3df;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

import java.util.List;

public class VectorEditorGame extends AbstractGame {

    private final String file = "C:\\Users\\Sergio\\IdeaProjects\\java-vectors\\javafx-vectors-showcase\\src\\main\\resources\\vectors.txt";
    
    private PanAndZoomGraphicContext pz;

    private Vec2df mouse;

    private Rect2df screen;

    private Rect2df arena;

    private Vec2df start;

    private Vec2df end;
    
    private List<Vec2dfColored> list;

    private Vec2df sel;
    
    private void drawVector(Vec2df start, Vec2df end) {
        pz.strokeLine(start, new Vec2df(end).add(start));
    }

    private void drawVector(Vec2df end) {
        drawVector(new Vec2df(), end);
    }

    private Vec2dfColored buildVec(Vec2df vec) {
        String name = "v" + list.size();
        Vec3df c = new Vec3df((float) Math.random(), (float) Math.random(), (float) Math.random());
        Vec2dfColored v = new Vec2dfColored(name, vec.getX(), vec.getY());
        v.setOffset(new Vec2df());
        v.setC(c);
        return v;
    }

    private Vec2dfColored buildVec(Vec2df offset, Vec2df vec) {
        String name = "v" + list.size();
        Vec3df c = new Vec3df((float) Math.random(), (float) Math.random(), (float) Math.random());
        Vec2dfColored v = new Vec2dfColored(name, vec.getX(), vec.getY());
        v.setOffset(offset);
        v.setC(c);
        return v;
    }

    private Vec2dfColored buildVecFromStartToEnd(Vec2df s, Vec2df e) {
        Vec2df dim = new Vec2df(e).sub(s);
        return buildVec(start, dim);
    }
    
    @Override
    public void initialize(GameApplication gc) {
        list = new Vec2dfColoredIO(file).read();

        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        mouse = new Vec2df();
        arena = new Rect2df(0, 0, 100, 100);

        start = null;
        end = null;
        sel = null;
    }

    @Override
    public void update(GameApplication gc, float dt) {
        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));

        if (gc.getInput().isButtonDown(MouseButton.PRIMARY)) {
            start = new Vec2df(this.mouse);
        }

        if (gc.getInput().isButtonUp(MouseButton.PRIMARY)) {
            if (sel != null) {

            } else {
                end = new Vec2df(this.mouse);

                list.add(buildVecFromStartToEnd(start, end));

                start = null;
                end = null;
            }
        }

        if (gc.getInput().isKeyDown(KeyCode.A)) {
            if (list.size() >= 2) {
                Vec2dfColored v = list.get(list.size() - 1);
                Vec2dfColored u = list.get(list.size() - 2);
                list.add(buildVec(new Vec2df(u).add(v)));
            }
        }
        if (gc.getInput().isKeyDown(KeyCode.S)) {
            if (list.size() >= 2) {
                Vec2dfColored v = list.get(list.size() - 1);
                Vec2dfColored u = list.get(list.size() - 2);
                list.add(buildVec(new Vec2df(u).sub(v)));
            }
        }
        if (gc.getInput().isKeyDown(KeyCode.M)) {
            if (list.size() >= 2) {
                Vec2dfColored v = list.get(list.size() - 1);
                Vec2dfColored u = list.get(list.size() - 2);
                list.add(buildVec(new Vec2df(u).mul(v)));
            }
        }
        if (gc.getInput().isKeyDown(KeyCode.D)) {
            if (list.size() >= 2) {
                Vec2dfColored v = list.get(list.size() - 1);
                Vec2dfColored u = list.get(list.size() - 2);
                list.add(buildVec(new Vec2df(u).div(v)));
            }
        }
        if (gc.getInput().isKeyDown(KeyCode.SPACE)) {
            Vec2dfColored v = list.get(list.size() - 1);
            Vec2dfColored u = list.get(list.size() - 2);
            System.out.println("Dot product: " + v.norm().dotProduct(u.norm()));
        }
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        // Dibujar el fondo
        screen = new Rect2df(pz.getWorldTopLeft(), pz.getWorldVisibleArea());
        g.setFill(Color.WHITE);
        pz.fillRect(screen.getPos(), screen.getSize());

        g.setStroke(Color.BLACK);

        // Dibujar el marco de la arena
        pz.getGc().setLineWidth(1);
        pz.strokeRect(arena.getPos(), arena.getSize());

        // Dibujar los vectores
        g.setLineWidth(pz.scaleToScreenX((float) g.getLineWidth()));
        for (var v : list) {
            pz.setStroke(v.getColor());
            drawVector(v.getOffset(), v);
        }

        // Dibujar el rectángulo seleccionado
        if (start != null) {
            g.setStroke(Color.YELLOW);
            g.setLineWidth(pz.scaleToScreenX((float) g.getLineWidth()));
            pz.strokeLine(start, this.mouse);
        }
    }

    @Override
    public void stop(GameApplication gc) {
        super.stop(gc);
        boolean isSaved = new Vec2dfColoredIO(file).save(list);
        if (isSaved) {
            System.out.println("Saved: ok");
        }
    }

}
