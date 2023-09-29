package main.collision;

import org.javafx.game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import main.PanAndZoomAbstractGame;
import main.editor.io.Rect2dfIO;
import org.geom.shape.rectangle.Rect2df;
import org.geom.shape.utils.ShapeOverlapUtils;
import org.geom.space.division.QuadTree2df;
import org.geom.vector.vec2d.Vec2df;
import org.geom.vector.vec2d.Vec2di;
import pan.and.zoom.PanAndZoomGraphicContext;

import java.util.ArrayList;
import java.util.List;

public class RectVsRectCollisionDetectionGame extends PanAndZoomAbstractGame {

    private final float GRAVITY = 1000;

    private final String fileName = "C:\\Users\\Sergio\\IdeaProjects\\java-vectors\\javafx-vectors-showcase\\src\\main\\resources\\map.txt";

    // private List<Rect2df> rects;

    private QuadTree2df<Rect2df> tree;

    private Rect2df r;

    private Rect2df searchArea;

    private Vec2df dir;

    private Vec2df contactPoint;

    private Vec2df contactNormal;

    private List<Pair<Rect2df, Float>> z;

    private boolean isCollision = false;

    private boolean showCollision = false;

    private long elapsedTime = 0;

    private void drawRect(PanAndZoomGraphicContext g, Rect2df r) {
        // g.strokeRect(r.getPos().getX(), r.getPos().getY(), r.getSize().getX(), r.getSize().getY());
        g.strokeRect(r.getPos(), r.getSize());
    }

    private void drawLine(PanAndZoomGraphicContext g, Vec2df s, Vec2df e, float lineWidth) {
        double cacheLineWidth = g.getGc().getLineWidth();
        g.getGc().setLineWidth(g.scaleToScreenX(lineWidth));
        g.strokeLine(s, e);
        g.getGc().setLineWidth(cacheLineWidth);
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
                    drawQuadTree(pz, children[i], screen);
                } else if (screen.overlaps(rectChildren[i])) {
                    drawQuadTree(pz, children[i], screen);
                }
            }
        }
    }

    @Override
    public void initialize(GameApplication gc) {
        super.initialize(gc);

        dir = new Vec2df();

        z = new ArrayList<>();

        tree = new QuadTree2df<>(arena);
        for (var r : new Rect2dfIO(fileName).read()) {
            tree.insert(r, new Rect2df(r));
        }
        r = new Rect2df(0, 0, 10.0f, 15.0f);
        tree.insert(r, new Rect2df(r));

        searchArea = new Rect2df();
    }

    @Override
    public void update(GameApplication gc, float dt) {
        super.update(gc, dt);

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
        }
        if (gc.getInput().isKeyHeld(KeyCode.S)) {
            this.dir.addToY(inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.A)) {
            this.dir.addToX(-inc);
        }
        if (gc.getInput().isKeyHeld(KeyCode.D)) {
            this.dir.addToX(inc);
        }

        if (gc.getInput().isKeyDown(KeyCode.TAB)) {
            showCollision = !showCollision;
        }

        final Vec2df g = new Vec2df(0, GRAVITY);
        g.mul(dt);
        dir.add(g).mul(dt);

        float t = 0;
        float minT = Float.MAX_VALUE;

        long t1 = System.nanoTime();
        // Search Area
        Vec2df tl = r.getTopLeft();
        Vec2df br = r.getBottomRight();
        if (dir.getX() > 0) {
            br.addToX(dir.getX());
        } else {
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
        contactPoint = new Vec2df();
        contactNormal = new Vec2df();
        isCollision = false;
        z.clear();
        for (var r : tree.search(searchArea)) {
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
        tree.insert(this.r, new Rect2df(this.r));
    }

    @Override
    public void render(GameApplication gc) {
        GraphicsContext g = gc.getGraphicsContext();

        // Dibujar el fondo
        g.setFill(Color.BLUE);
        pz.fillRect(screen.getPos(), screen.getSize());

        // Configurar el color y el grosor de la línea
        final float lineWidth = 1;
        g.setStroke(Color.WHITE);

        // Dibujar el marco de la arena
        pz.strokeRect(arena.getPos(), arena.getSize());

        // Dibujar los rectángulos en pantalla
        List<Rect2df> rectOnScreen = tree.search(screen);
        for (var r : rectOnScreen) {
            g.setStroke(Color.WHITE);
            drawRect(pz, r);
        }

        // Dibujar la dirección del jugador
        final float scale = 5f;
        Vec2df start = new Vec2df(r.getPos()).add(new Vec2df(r.getSize()).div(new Vec2df(2)));
        Vec2df end = new Vec2df(r.getPos()).add(new Vec2df(dir).mul(scale)).add(new Vec2df(r.getSize()).div(new Vec2df(2)));
        drawLine(pz, start, end, lineWidth);

        // Dibujar la colisión
        if (isCollision) {
            final float radius = 1;
            g.setStroke(Color.RED);
            pz.strokeOval(new Vec2df(contactPoint).sub(new Vec2df(radius)), new Vec2df(radius * 2f));
            drawLine(pz, contactPoint, new Vec2df(contactPoint).add(contactNormal.mul(scale)), lineWidth);
        }

        // Debug
        if (showCollision) {
            g.setStroke(Color.YELLOW);
            drawRect(pz, searchArea);

            g.setStroke(Color.ORANGE);
            drawQuadTree(pz, tree, screen);

            final Vec2di textPos = new Vec2di(10, 30);
            String str = String.format("Elapsed time: %.3f milliseconds\n", elapsedTime / 1000000f);
            g.setFill(Color.WHITE);
            g.fillText(str, textPos.getX(), textPos.getY());
        }

    }

}
