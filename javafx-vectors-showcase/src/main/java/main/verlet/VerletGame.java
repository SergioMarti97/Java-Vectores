package main.verlet;

import org.javafx.game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import main.PanAndZoomAbstractGame;
import org.geom.vector.vec2d.Vec2df;
import org.geom.vector.vec2d.Vec2di;
import pan.and.zoom.PanAndZoomGraphicContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerletGame extends PanAndZoomAbstractGame {

    private List<VerletRect2df> l;

    private Vec2df dir;

    private final float gravity = -9.8f;

    private void drawLine(PanAndZoomGraphicContext g, Vec2df s, Vec2df e, float lineWidth) {
        double cacheLineWidth = g.getGc().getLineWidth();
        g.getGc().setLineWidth(g.scaleToScreenX(lineWidth));
        g.strokeLine(s, e);
        g.getGc().setLineWidth(cacheLineWidth);
    }

    @Override
    public void initialize(GameApplication gc) {
        super.initialize(gc);
        final float density = 3.0f;

        VerletRect2df r1 = new VerletRect2df(0, 0, 10.0f, 15.0f);
        r1.setMass(r1.getSize().getX() * r1.getSize().getY() * density);

        VerletRect2df r2 = new VerletRect2df(0, 25, 20.0f, 30.0f);
        r2.setMass(r2.getSize().getX() * r2.getSize().getY() * density);

        l = new ArrayList<>(Arrays.asList(r1, r2));

        dir = new Vec2df();
    }

    @Override
    public void update(GameApplication gc, float dt) {
        super.update(gc, dt);

        dir.set(0);
        final float inc = 10000;
        if (gc.getInput().isKeyHeld(KeyCode.W)) {
            dir.addToY(- inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.S)) {
            dir.addToY(inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.A)) {
            dir.addToX(-inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.D)) {
            dir.addToX(inc);
        }
        if (gc.getInput().isKeyDown(KeyCode.SPACE)) {
            dir.addToY(- 10 * inc);
        }

        for (var r : l) {
            // Constant air resistance
            // Fr(air) = c * v^2
            final float c = 10000;
            Vec2df v = r.calVelocity();
            Vec2df fResistance = new Vec2df(v).mul(v).mul(c);

            // Addition of forces
            Vec2df f = new Vec2df(dir).sub(fResistance);

            r.applyForce(f);
            r.accelerate(new Vec2df(0.0f, -gravity));
            r.doVerletStep(dt);

            // Ground
            final float ground = 50;
            if (r.getPos().getY() + r.getSize().getY() >= ground) {
                float high = ground - r.getSize().getY();
                r.getPos().setY(high);
                r.getOldPos().setY(high);
            }
        }
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        // Dibujar el fondo
        g.setFill(Color.BLUE);
        pz.fillRect(screen.getPos(), screen.getSize());

        g.setStroke(Color.WHITE);

        // Dibujar el marco de la arena
        pz.getGc().setLineWidth(1);
        pz.strokeRect(arena.getPos(), arena.getSize());

        // Dibujar los rectángulos
        final int textLeadingInc = 30;
        final Vec2di textPos = new Vec2di(10, 30);
        g.setFill(Color.YELLOW);
        for (var r : l) {
            pz.strokeRect(r.getPos(), r.getSize());

            Vec2df v = r.calVelocity();
            g.fillText(String.format("Velocity: %s", v.toString()), textPos.getX(), textPos.getY());
            textPos.addToY(textLeadingInc);
        }
    }

}
