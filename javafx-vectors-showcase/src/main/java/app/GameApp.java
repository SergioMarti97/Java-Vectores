package app;

import game.GameApplication;
import main.collision.RectVsRectCollisionDetectionGame;
import main.editor.rect.RectMapEditorGame;
import main.editor.vec.VectorEditorGame;
import main.spline.SplineGame;

public class GameApp extends GameApplication {

    @Override
    public void init() throws Exception {
        super.init();
        setAppName("Continuos Collision Game");
        setGame(new RectVsRectCollisionDetectionGame());
    }

}
