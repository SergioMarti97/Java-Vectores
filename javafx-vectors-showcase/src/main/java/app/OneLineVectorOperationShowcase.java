package app;

import org.geom.vector.vec2d.Vec2df;

public class OneLineVectorOperationShowcase {

    static Vec2df v;

    static Vec2df u;

    public static void main(String[] args) {
        // Vec2df v1 = new Vec2df(0.5f, 0.75f);
        // Vec2df v2 = new Vec2df(0.1f, 0.15f);
        // v2.add(v1).sub(new Vec2df(0.4f, 0.3f)).mul(new Vec2df(10)).norm();
        // System.out.println(v2);

        v = new Vec2df(-1, 2);
        u = new Vec2df(3, -1);
        System.out.printf("%s\n%s\n", v, u);
        v.sub(u);
        System.out.println(v);

        u.set(-1, 3);
        v.set(3, 0);
        System.out.printf("u: %s\nv: %s\n", u, v);
        System.out.printf("projection v over u: %.5f\n", u.projection(v));
    }

}
