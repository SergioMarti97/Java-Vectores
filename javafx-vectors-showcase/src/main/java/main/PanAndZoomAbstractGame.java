package main;

import game.AbstractGame;
import game.GameApplication;
import game.input.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

public abstract class PanAndZoomAbstractGame extends AbstractGame {

    protected PanAndZoomGraphicContext pz;

    protected Vec2df mouse;

    protected Rect2df screen;

    protected Rect2df arena;

    protected void setMouse(Input i) {
        mouse.set((float) i.getMouseX(), (float) i.getMouseY());
    }

    @Override
    public void initialize(GameApplication gc) {
        final float arenaSize = 1000;
        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        arena = new Rect2df(0, 0, arenaSize, arenaSize);
        screen = new Rect2df(pz.getWorldTopLeft(), pz.getWorldVisibleArea());
        mouse = new Vec2df();
        setMouse(gc.getInput());
    }

    @Override
    public void update(GameApplication gc, float v) {
        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));
        screen.set(pz.getWorldTopLeft(), pz.getWorldVisibleArea());
    }

}
