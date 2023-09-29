package main.spline;

import org.javafx.game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import main.PanAndZoomAbstractGame;
import org.geom.shape.rectangle.Rect2df;
import org.geom.spline.Spline2df;
import org.geom.vector.vec2d.Vec2df;
import pan.and.zoom.PanAndZoomGraphicContext;

import java.util.ArrayList;
import java.util.List;

public class SplineGame extends PanAndZoomAbstractGame {

    private Spline2df path;

    private int idSelectedPoint = 0;

    private void drawLine(PanAndZoomGraphicContext g, Vec2df s, Vec2df e, float lineWidth) {
        double cacheLineWidth = g.getGc().getLineWidth();
        g.getGc().setLineWidth(g.scaleToScreenX(lineWidth));
        g.strokeLine(s, e);
        g.getGc().setLineWidth(cacheLineWidth);
    }
    
    @Override
    public void initialize(GameApplication gc) {
        super.initialize(gc);
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
        super.update(gc, dt);

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

        // lines
        final float inc = 0.05f;
        List<Vec2df> l = new ArrayList<>();
        for (float t = 0.0f; t < (float) path.getP().size() - 3; t += inc) {
            l.add(path.getSplinePoint(t, false));
        }
        int i;
        Vec2df s, e;

        final float lineWidth = 1f;
        double cacheLineWidth = pz.getGc().getLineWidth();
        pz.getGc().setLineWidth(pz.scaleToScreenX(lineWidth));
        for (i = 0; i < l.size() - 2; i++) {
            s = l.get(i);
            e = l.get(i + 1);
            pz.strokeLine(s, e);
        }
        s = l.get(i);
        e = l.get(i + 1);
        pz.strokeLine(s, e);
        pz.getGc().setLineWidth(cacheLineWidth);

        // dots
        pz.setStroke(Color.RED);
        final float radius = 0.5f;
        for (i = 0; i < path.getP().size(); i++) {
            var p = path.getP().get(i);
            g.setStroke(i != idSelectedPoint ? Color.RED : Color.YELLOW);
            pz.strokeOval(new Vec2df(p).sub(new Vec2df(radius)), new Vec2df(radius * 2f));
        }

    }
}
