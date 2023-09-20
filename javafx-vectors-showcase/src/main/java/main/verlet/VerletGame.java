package main.verlet;

import game.GameApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.PanAndZoomAbstractGame;

public class VerletGame extends PanAndZoomAbstractGame {

    @Override
    public void initialize(GameApplication gc) {
        super.initialize(gc);
    }

    @Override
    public void update(GameApplication gc, float dt) {
        super.update(gc, dt);
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
    }

}
