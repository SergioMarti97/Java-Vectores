package org.geom.spline;

import org.geom.vector.vec2d.Vec2df;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spline2df extends Spline<Vec2df> {

    public Spline2df(Vec2df... p) {
        this.p = new ArrayList<>(Arrays.asList(p));
    }

    public Vec2df getSplinePoint(float t, boolean isLoop) {
        int p0, p1, p2, p3;

        if (!isLoop) {
            p1 = (int)t + 1;
            p2 = p1 + 1;
            p3 = p2 + 1;
            p0 = p1 - 1;
        } else {
            p1 = (int) t;
            p2 = (p1 + 1) % p.size();
            p3 = (p2 + 1) % p.size();
            p0 = p1 > 1 ? p1 - 1 : p.size() - 1;
        }

        t -= (int)t;

        float tt = t * t;
        float ttt = tt * t;

        float q1 = -ttt + 2.0f * tt - t;
        float q2 = 3.0f * ttt - 5.0f * tt + 2.0f;
        float q3 = -3.0f * ttt + 4.0f * tt + t;
        float q4 = ttt - tt;

        float tx = this.p.get(p0).getX() * q1 + this.p.get(p1).getX() * q2 + this.p.get(p2).getX() * q3 + this.p.get(p3).getX() * q4;
        float ty = this.p.get(p0).getY() * q1 + this.p.get(p1).getY() * q2 + this.p.get(p2).getY() * q3 + this.p.get(p3).getY() * q4;

        tx *= 0.5f;
        ty *= 0.5f;

        return new Vec2df(tx, ty);
    }

}
