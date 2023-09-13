package main;

import game.AbstractGame;
import game.GameApplication;
import javafx.scene.input.MouseButton;
import org.geom.vector.vec2d.Vec2df;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

public class RectMapEditorGame extends AbstractGame {

    private PanAndZoomGraphicContext pz;

    private Vec2df mouse;
    
    @Override
    public void initialize(GameApplication gc) {
        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        mouse = new Vec2df();
    }

    @Override
    public void update(GameApplication gc, float v) {
        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        // this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));
    }

    @Override
    public void render(GameApplication gc) {

    }

}
