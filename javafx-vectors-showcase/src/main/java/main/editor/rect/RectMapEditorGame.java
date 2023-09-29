package main.editor.rect;

import org.javafx.game.AbstractGame;
import org.javafx.game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import main.editor.io.Rect2dfIO;
import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

import java.util.List;

public class RectMapEditorGame extends AbstractGame {

    private final String fileName = "C:\\Users\\Sergio\\IdeaProjects\\java-vectors\\javafx-vectors-showcase\\src\\main\\resources\\map.txt";

    private PanAndZoomGraphicContext pz;

    private Vec2df mouse;

    private Rect2df screen;

    private Rect2df arena;

    private List<Rect2df> rects;

    private Vec2df start;

    private Vec2df end;

    private Vec2df mouseOffset;

    private Rect2df selectedRect;

    private void drawRect(PanAndZoomGraphicContext g, Rect2df r) {
        g.strokeRect(r.getPos(), r.getSize());
    }
    
    @Override
    public void initialize(GameApplication gc) {
        rects = new Rect2dfIO(fileName).read();

        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        mouse = new Vec2df();
        arena = new Rect2df(0, 0, 1000, 1000);

        start = null;
        end = null;
        mouseOffset = new Vec2df();
        selectedRect = null;
    }

    @Override
    public void update(GameApplication gc, float v) {
        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));

        if (gc.getInput().isButtonDown(MouseButton.PRIMARY)) {
            start = new Vec2df(this.mouse);
            for (var r : rects) {
                if (r.contains(this.mouse)) {
                    mouseOffset = new Vec2df(this.mouse).sub(r.getPos());
                    selectedRect = r;
                    break;
                }
            }
            rects.remove(selectedRect);
        }

        if (gc.getInput().isButtonUp(MouseButton.PRIMARY)) {
            if (selectedRect != null) {
                selectedRect.setPos(new Vec2df(this.mouse).sub(mouseOffset));
                rects.add(selectedRect);
                selectedRect = null;
            } else {
                end = new Vec2df(this.mouse);
                rects.add(new Rect2df(start, new Vec2df(end).sub(start)));

                start = null;
                end = null;
            }
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

        // Dibujar los rectángulos
        for (var r : rects) {
            drawRect(pz, r);
        }

        // Dibujar el rectángulo seleccionado
        if (start != null) {
            g.setStroke(Color.YELLOW);
            Rect2df r = new Rect2df(start, new Vec2df(mouse).sub(start));
            drawRect(pz, r);
        }

        // Dibujar el rectángulo seleccionado con el ratón
        if (selectedRect != null) {
            g.setStroke(Color.YELLOW);
            drawRect(pz, new Rect2df(new Vec2df(mouse).sub(mouseOffset), selectedRect.getSize()));
        }
    }

    @Override
    public void stop(GameApplication gc) {
        super.stop(gc);
        boolean isSaved = new Rect2dfIO(fileName).save(rects);
        if (isSaved) {
            System.out.println("Saved: ok");
        }
    }

}
