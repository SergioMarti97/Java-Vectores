package main.collision;

import org.javafx.game.AbstractGame;
import org.javafx.game.GameApplication;
import org.javafx.game.input.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.geom.shape.rectangle.Rect2df;
import org.geom.shape.utils.ShapeOverlapUtils;
import org.geom.vector.vec2d.Vec2df;

public class RayVsRectCollisionDetectionGame extends AbstractGame {

    private Vec2df ori;

    private Vec2df dir;

    private Vec2df mouse;

    private Rect2df r;

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
        gc.getGraphicsContext().setLineWidth(5);

        mouse = new Vec2df();
        setMouse(gc.getInput());

        ori = new Vec2df(20, 20);
        dir = new Vec2df(mouse);
        dir.sub(ori);

        r = buildRectCenteredOnScreen(gc.getWidth(), gc.getHeight(), 0.33f);

        System.out.println(r);
    }

    @Override
    public void update(GameApplication gc, float dt) {
        setMouse(gc.getInput());

        if (gc.getInput().isButtonDown(MouseButton.PRIMARY)) {
            ori.set(mouse);
        }

        dir = new Vec2df(mouse);
        dir.sub(ori);
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        g.setFill(Color.BLUE);
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

        g.setStroke(Color.WHITE);
        g.strokeLine(ori.getX(), ori.getY(), mouse.getX(), mouse.getY());

        Vec2df contact = new Vec2df();
        Vec2df contactNormal = new Vec2df();
        float t = ShapeOverlapUtils.rayVsRect(ori, dir, r, contact, contactNormal);

        g.setStroke(t > -1 && t < 1 ? Color.YELLOW : Color.WHITE);
        drawRect(gc.getGraphicsContext(), r);

        if (t > -1 && t < 1) {
            final float radius = 10;
            g.setStroke(Color.RED);
            g.strokeOval(contact.getX() - radius, contact.getY() - radius, radius * 2, radius * 2);

            final float normalScale = 100;
            contactNormal.mul(normalScale);
            g.strokeLine(contact.getX(), contact.getY(), contact.getX() + contactNormal.getX(), contact.getY() + contactNormal.getY());

            final Vec2df textOffset = new Vec2df(20, 20);
            textOffset.add(contact);
            g.setFill(Color.RED);
            g.fillText(String.format("%.3f", t), textOffset.getX(), textOffset.getY());
        }
    }

}
