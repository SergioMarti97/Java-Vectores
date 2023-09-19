package main.spline;

import game.AbstractGame;
import game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.geom.shape.rectangle.Rect2df;
import org.geom.spline.Spline2df;
import org.geom.vector.vec2d.Vec2df;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

import java.util.ArrayList;
import java.util.List;

public class SplineGame extends AbstractGame {

    private PanAndZoomGraphicContext pz;

    private Vec2df mouse;

    private Rect2df screen;

    private Rect2df arena;

    private Spline2df path;

    private int idSelectedPoint = 0;
    
    @Override
    public void initialize(GameApplication gc) {
        gc.getGraphicsContext().setLineWidth(0.1);

        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        mouse = new Vec2df();
        arena = new Rect2df(0, 0, 100, 100);

        path = new Spline2df(
                new Vec2df(10, 41),
                new Vec2df(40, 41),
                new Vec2df(70, 41),
                new Vec2df(100, 41)
        );
    }

    @Override
    public void update(GameApplication gc, float dt) {
        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));

        // Spline control

        if (gc.getInput().isKeyDown(KeyCode.X)) {
            idSelectedPoint++;
            if (idSelectedPoint >= path.getP().size()) {
                idSelectedPoint = 0;
            }
        }
        if (gc.getInput().isKeyDown(KeyCode.Z)) {
            idSelectedPoint--;
            if (idSelectedPoint < 0) {
                idSelectedPoint = path.getP().size() - 1;
            }
        }
        final float vel = 30f;
        if (gc.getInput().isKeyHeld(KeyCode.UP)) {
            path.getP().get(idSelectedPoint).subToY(vel * dt);
        }
        if (gc.getInput().isKeyHeld(KeyCode.DOWN)) {
            path.getP().get(idSelectedPoint).addToY(vel * dt);
        }
        if (gc.getInput().isKeyHeld(KeyCode.LEFT)) {
            path.getP().get(idSelectedPoint).subToX(vel * dt);
        }
        if (gc.getInput().isKeyHeld(KeyCode.RIGHT)) {
            path.getP().get(idSelectedPoint).addToX(vel * dt);
        }
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        // Dibujar el fondo
        screen = new Rect2df(pz.getWorldTopLeft(), pz.getWorldVisibleArea());
        g.setFill(Color.BLUE);
        pz.fillRect(screen.getPos(), screen.getSize());

        g.setStroke(Color.WHITE);

        // Dibujar el marco de la arena
        pz.getGc().setLineWidth(1);
        pz.strokeRect(arena.getPos(), arena.getSize());

        // draw spline
        pz.setStroke(Color.WHITE);
        final float inc = 0.05f;
        List<Vec2df> l = new ArrayList<>();
        for (float t = 0.0f; t < (float) path.getP().size() - 3; t += inc) {
            l.add(path.getSplinePoint(t, false));
        }
        int i;
        Vec2df s, e;
        for (i = 0; i < l.size() - 2; i++) {
            s = l.get(i);
            e = l.get(i + 1);
            pz.strokeLine(s, e);
        }
        s = l.get(i);
        e = l.get(i + 1);
        pz.strokeLine(s, e);

        pz.setStroke(Color.RED);
        final float radius = 0.5f;
        for (i = 0; i < path.getP().size(); i++) {
            var p = path.getP().get(i);
            g.setStroke(i != idSelectedPoint ? Color.RED : Color.YELLOW);
            pz.strokeOval(new Vec2df(p).sub(new Vec2df(radius)), new Vec2df(radius * 2f));
        }
    }
}
