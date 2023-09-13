package app;

import game.GameApplication;
import main.RayVsRectCollisionDetectionGame;
import main.RectMapEditorGame;
import main.RectVsRectCollisionDetectionGame;

public class GameApp extends GameApplication {

    @Override
    public void init() throws Exception {
        super.init();
        setAppName("Continuos Collision Game");
        setGame(new RectMapEditorGame());
    }

}
