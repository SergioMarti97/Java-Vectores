package main;

import game.AbstractGame;
import game.GameApplication;
import game.input.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.geom.shape.rectangle.Rect2df;
import org.geom.shape.utils.ShapeOverlapUtils;
import org.geom.vector.vec2d.Vec2df;

import java.util.ArrayList;
import java.util.List;

public class RectVsRectCollisionDetectionGame extends AbstractGame {

    private Vec2df mouse;

    private Rect2df r;

    private Vec2df dir;

    private List<Rect2df> rects;

    private Vec2df contactPoint;

    private Vec2df contactNormal;

    private List<Pair<Integer, Float>> z;

    private boolean isCollision = false;

    private void setMouse(Input i) {
        mouse.set((float) i.getMouseX(), (float) i.getMouseY());
    }

    private void drawRect(GraphicsContext g, Rect2df r) {
        g.strokeRect(r.getPos().getX(), r.getPos().getY(), r.getSize().getX(), r.getSize().getY());
    }

    private Rect2df buildRectCenteredOnScreen(float screenW, float screenH, final float percent) {
        float w = screenW * percent;
        float h = screenH * percent;
        return new Rect2df(
                screenW / 2f - w / 2f,
                screenH / 2f - h / 2f,
                w, h
        );
    }

    @Override
    public void initialize(GameApplication gc) {
        gc.getGraphicsContext().setLineWidth(2);

        contactPoint = null;
        contactNormal = null;

        z = new ArrayList<>();

        mouse = new Vec2df();
        setMouse(gc.getInput());

        dir = new Vec2df();

        rects = new ArrayList<>();
        // rects.add(buildRectCenteredOnScreen(gc.getWidth(), gc.getHeight(), 0.33f));
        rects.add(new Rect2df( 150.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 170.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 190.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 210.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 230.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 250.0f, 500.0f, 20.0f, 20.0f));
        rects.add(new Rect2df( 250.0f, 500.0f, 20.0f, 20.0f));
        
        // r = new Rect2df(10, 10, 50, 80);
        r = new Rect2df(170.0f, 70.0f, 10.0f, 40.0f);
    }

    @Override
    public void update(GameApplication gc, float dt) {
        contactPoint = null;
        contactNormal = null;

        setMouse(gc.getInput());

        if (gc.getInput().isButtonHeld(MouseButton.PRIMARY)) {
            Vec2df dir = new Vec2df(mouse);
            dir.sub(r.getPos());
            dir.normalize();
            dir.mul(5000 * dt);
            this.dir.add(dir);
        }

        if (gc.getInput().isKeyHeld(KeyCode.W)) {
            this.dir.addToY(-100);
        }
        if (gc.getInput().isKeyHeld(KeyCode.S)) {
            this.dir.addToY(100);
        }
        if (gc.getInput().isKeyHeld(KeyCode.A)) {
            this.dir.addToX(-100);
        }
        if (gc.getInput().isKeyHeld(KeyCode.D)) {
            this.dir.addToX(100);
        }

        final Vec2df g = new Vec2df(0, 2500);
        g.mul(dt);
        dir.add(g);

        dir.mul(dt);

        isCollision = false;

        contactPoint = new Vec2df();
        contactNormal = new Vec2df();

        float t = 0;
        float minT = Float.MAX_VALUE;
        z.clear();

        for (int i = 0; i < rects.size(); i++) {
            Rect2df r = rects.get(i);
            t = ShapeOverlapUtils.rectVsRect(this.r, this.dir, r, contactPoint, contactNormal);
            if (t > -1 && t < 1) {
                isCollision = true;
                System.out.println("Collision ocurred: " + contactPoint);
                z.add(new Pair<>(i, t));
            }
        }

        // z.sort((p1, p2) -> Float.compare(p2.getValue(), p1.getValue()));

        for (var pair : z) {
            Rect2df r = rects.get(pair.getKey());
            ShapeOverlapUtils.resolveRectVsRect(this.r, this.dir, r, contactPoint, contactNormal);
        }

        r.getPos().add(dir);
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        g.setFill(Color.BLUE);
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

        g.setStroke(Color.WHITE);

        g.strokeLine(
                r.getPos().getX() + r.getSize().getX() / 2,
                r.getPos().getY() + r.getSize().getY() / 2,
                r.getPos().getX() + r.getSize().getX() / 2 + this.dir.getX(),
                r.getPos().getY() + r.getSize().getY() / 2 + this.dir.getY());

        drawRect(g, this.r);

        for (var r : rects) {
            g.setStroke(Color.WHITE);
            drawRect(g, r);
        }

        if (isCollision) {
            final float radius = 10;
            g.setStroke(Color.RED);
            g.strokeOval(contactPoint.getX() - radius, contactPoint.getY() - radius, radius * 2, radius * 2);

            final float normalScale = 100;
            contactNormal.mul(normalScale);
            g.strokeLine(contactPoint.getX(), contactPoint.getY(), contactPoint.getX() + contactNormal.getX(), contactPoint.getY() + contactNormal.getY());
        }

    }

}
