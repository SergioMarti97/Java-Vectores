package app;

import org.geom.vector.vec2d.Vec2df;

public class OneLineVectorOperationShowcase {

    public static void main(String[] args) {
        Vec2df v1 = new Vec2df(0.5f, 0.75f);
        Vec2df v2 = new Vec2df(0.1f, 0.15f);
        v2.add(v1).sub(new Vec2df(0.4f, 0.3f)).mul(new Vec2df(10)).norm();
    }

}
