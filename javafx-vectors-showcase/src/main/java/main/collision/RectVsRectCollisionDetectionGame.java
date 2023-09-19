package main.collision;

import game.AbstractGame;
import game.GameApplication;
import game.input.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.collision.verlet.VerletRect2df;
import main.editor.io.Rect2dfIO;
import org.geom.shape.rectangle.Rect2df;
import org.geom.shape.utils.ShapeOverlapUtils;
import org.geom.space.division.QuadTree2df;
import org.geom.spline.Spline2df;
import org.geom.vector.vec2d.Vec2df;
import org.geom.vector.vec2d.Vec2di;
import pan.and.zoom.PanAndZoomGraphicContext;
import pan.and.zoom.PanAndZoomUtils;

import java.util.ArrayList;
import java.util.List;

public class RectVsRectCollisionDetectionGame extends AbstractGame {

    private final String fileName = "C:\\Users\\Sergio\\IdeaProjects\\java-vectors\\javafx-vectors-showcase\\src\\main\\resources\\map.txt";

    private PanAndZoomGraphicContext pz;

    private Vec2df mouse;

    private Rect2df screen;

    private Rect2df arena;

    private Rect2df r;

    private Rect2df searchArea;

    private Vec2df dir;

    // private List<Rect2df> rects;

    private Vec2df contactPoint;

    private Vec2df contactNormal;

    private List<Pair<Rect2df, Float>> z;

    private boolean isCollision = false;

    private boolean showCollision = false;

    private QuadTree2df<Rect2df> tree;

    private final float GRAVITY = 1000;

    private long elapsedTime = 0;

    private void setMouse(Input i) {
        mouse.set((float) i.getMouseX(), (float) i.getMouseY());
    }

    private void drawRect(PanAndZoomGraphicContext g, Rect2df r) {
        // g.strokeRect(r.getPos().getX(), r.getPos().getY(), r.getSize().getX(), r.getSize().getY());
        g.strokeRect(r.getPos(), r.getSize());
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

    private <T> void drawQuadTree(PanAndZoomGraphicContext pz, QuadTree2df<T> tree, Rect2df screen) {
        var rect = tree.getRect();
        if (screen.contains(rect)) {
            pz.strokeRect(rect.getPos(), rect.getSize());
        }
        final int NUM_CHILDREN = 4;
        var children = tree.getChildren();
        var rectChildren = tree.getRectChildren();
        for (int i = 0; i < NUM_CHILDREN; i++) {
            if (children[i] != null) {
                if (screen.contains(rectChildren[i])) {
                    // children[i].draw(pz, screen);
                    drawQuadTree(pz, children[i], screen);
                } else if (screen.overlaps(rectChildren[i])) {
                    // children[i].draw(pz, screen);
                    drawQuadTree(pz, children[i], screen);
                }
            }
        }
    }

    @Override
    public void initialize(GameApplication gc) {
        gc.getGraphicsContext().setLineWidth(0.1);

        pz = new PanAndZoomGraphicContext(gc.getGraphicsContext());
        mouse = new Vec2df();
        arena = new Rect2df(0, 0, 1000, 1000);

        contactPoint = null;
        contactNormal = null;

        z = new ArrayList<>();

        mouse = new Vec2df();
        setMouse(gc.getInput());

        dir = new Vec2df();

        tree = new QuadTree2df<>(arena);
        List<Rect2df> listRect = new Rect2dfIO(fileName).read();
        for (var r : listRect) {
            tree.insert(r, new Rect2df(r));
        }
        // r = new Rect2df(0, 0, 10.0f, 15.0f);
        r = new VerletRect2df(0, 0, 10.0f, 15.0f);
        tree.insert(r, new Rect2df(r));

        searchArea = new Rect2df();
    }

    @Override
    public void update(GameApplication gc, float dt) {
        contactPoint = null;
        contactNormal = null;

        pz.handlePanAndZoom(gc, MouseButton.MIDDLE, 0.001f, true, true);
        Vec2df mouse = new Vec2df((float)gc.getInput().getMouseX(), (float)gc.getInput().getMouseY());
        this.mouse.set(PanAndZoomUtils.screenToWorld(mouse, pz.getWorldOffset(), pz.getWorldScale()));

        final float scale = 5000;
        if (gc.getInput().isButtonHeld(MouseButton.PRIMARY)) {
            Vec2df dir = new Vec2df(mouse);
            dir.sub(r.getPos());
            dir.normalize();
            dir.mul(scale * dt);
            this.dir.add(dir);
        }

        final float inc = 100;
        if (gc.getInput().isKeyHeld(KeyCode.W)) {
            this.dir.addToY(-2 * inc);
            // r.getAcc().addToY(-2 * inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.S)) {
            this.dir.addToY(inc);
            // r.getAcc().addToY(inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.A)) {
            this.dir.addToX(-inc);
            // r.getAcc().addToX(-inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.D)) {
            this.dir.addToX(inc);
            // r.getAcc().addToX(inc);
        }

        if (gc.getInput().isKeyDown(KeyCode.SPACE)) {
            showCollision = !showCollision;
        }

        final Vec2df g = new Vec2df(0, GRAVITY);
        g.mul(dt);
        dir.add(g);
        dir.mul(dt);

        isCollision = false;

        contactPoint = new Vec2df();
        contactNormal = new Vec2df();

        float t = 0;
        float minT = Float.MAX_VALUE;
        z.clear();

        /*for (int i = 0; i < rects.size(); i++) {
            Rect2df r = rects.get(i);
            t = ShapeOverlapUtils.rectVsRect(this.r, this.dir, r, contactPoint, contactNormal);
            if (t > -1 && t < 1) {
                isCollision = true;
                System.out.println("Collision ocurred: " + contactPoint);
                z.add(new Pair<>(i, t));
            }
        }*/
        long t1 = System.nanoTime();
        // Search Area
        Vec2df tl = r.getTopLeft();
        Vec2df br = r.getBottomRight();
        if (dir.getX() > 0) { // x positive
            br.addToX(dir.getX());
        } else { // x negative
            tl.addToX(dir.getX());
        }
        if (dir.getY() > 0) {
            br.addToY(dir.getY());
        } else {
            tl.addToY(dir.getY());
        }
        Vec2df size = new Vec2df(br).sub(tl);
        searchArea.set(tl, size);

        // Look for collisions
        List<Rect2df> potentialCollision = tree.search(searchArea);
        for (Rect2df r : potentialCollision) {
            t = ShapeOverlapUtils.rectVsRect(this.r, this.dir, r, contactPoint, contactNormal);
            if (t > -1 && t < 1) {
                isCollision = true;
                z.add(new Pair<>(r, t));
            }
        }

        // Sort by t
        z.sort((p1, p2) -> Float.compare(p2.getValue(), p1.getValue()));

        for (var pair : z) {
            var r = pair.getKey();
            ShapeOverlapUtils.resolveRectVsRect(this.r, this.dir, r, contactPoint, contactNormal);
        }

        long t2 = System.nanoTime();
        elapsedTime = (t2 - t1);

        // Update player position
        tree.remove(this.r, new Rect2df(this.r));
        r.getPos().add(dir);
        // r.verletStep(dt);
        tree.insert(this.r, new Rect2df(this.r));
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

        /*for (var r : rects) {
            g.setStroke(Color.WHITE);
            drawRect(pz, r);
        }*/
        List<Rect2df> rectOnScreen = tree.search(screen);
        for (var r : rectOnScreen) {
            g.setStroke(Color.WHITE);
            drawRect(pz, r);
        }

        /*g.strokeLine(
                r.getPos().getX() + r.getSize().getX() / 2,
                r.getPos().getY() + r.getSize().getY() / 2,
                r.getPos().getX() + r.getSize().getX() / 2 + this.dir.getX(),
                r.getPos().getY() + r.getSize().getY() / 2 + this.dir.getY());*/
        Vec2df start = new Vec2df(r.getPos()).add(new Vec2df(r.getSize()).div(new Vec2df(2)));
        Vec2df end = new Vec2df(r.getPos()).add(dir).add(new Vec2df(r.getSize()).div(new Vec2df(2)));
        pz.strokeLine(start, end);

        drawRect(pz, this.r);

        if (showCollision) {
            g.setStroke(Color.YELLOW);
            drawRect(pz, searchArea);
        }

        if (isCollision) {
            final float radius = 1;
            g.setStroke(Color.RED);
            // g.strokeOval(contactPoint.getX() - radius, contactPoint.getY() - radius, radius * 2, radius * 2);
            pz.strokeOval(new Vec2df(contactPoint).sub(new Vec2df(radius)), new Vec2df(radius * 2f));

            final float normalScale = 5;
            contactNormal.mul(normalScale);
            pz.strokeLine(contactPoint, new Vec2df(contactPoint).add(contactNormal));
        }

        if (showCollision) {
            g.setStroke(Color.ORANGE);
            drawQuadTree(pz, tree, screen);

            final Vec2di textPos = new Vec2di(10, 30);
            String str = String.format("Elapsed time: %.3f milliseconds\n", elapsedTime / 1000000f);
            g.setFill(Color.WHITE);
            g.fillText(str, textPos.getX(), textPos.getY());
        }

    }

}
