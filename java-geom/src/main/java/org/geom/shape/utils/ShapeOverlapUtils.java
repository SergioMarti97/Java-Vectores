package org.geom.shape.utils;

import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;

public class ShapeOverlapUtils {

    public static boolean doIntervalsOverlap(float x1, float x2, float y1, float y2) {
        return Math.max(x1, y1) <= Math.min(x2, y2);
    }

    public static boolean pointVsRect(Vec2df p, Rect2df r) {
        return p.getX() >= r.getPos().getX() &&
                p.getY() >= r.getPos().getY() &&
                p.getX() < r.getPos().getX() + r.getSize().getX() &&
                p.getY() < r.getPos().getY() + r.getSize().getY();
    }

    // Linear interpolation: lerp

    public static float lerp(float start, float end, float t) {
        return start * (1 - t) + end * t;
    }

    public static Vec2df lerp(Vec2df start, Vec2df end, float t) {
        return new Vec2df(lerp(start.getX(), end.getX(), t), lerp(start.getY(), end.getY(), t));
    }

    // Rect vs Rect

    public static boolean rectVsRect(Rect2df r1, Rect2df r2) {
        return (r1.getPos().getX() < r2.getPos().getX() + r2.getSize().getX() &&
                r1.getPos().getX() + r1.getSize().getX() > r2.getPos().getX() &&
                r1.getPos().getY() < r2.getPos().getY() + r2.getSize().getY() &&
                r1.getPos().getY() + r1.getSize().getY() > r2.getPos().getY());
    }

    // Ray vs Rect

    /**
     * Calcule the "t value". If the t value is less than 1 and greater than -1, there is a collision
     * @param ori origin of the ray
     * @param dir direction of the ray
     * @param target the rectangle
     * @param contactPoint contact point where the ray intersects with the rectangle
     * @param contactNormal the normal to the side of the rectangle where the contact point is
     * @return returns the "t" value. This is the normalize value on the ray where there is the contact point with the rectangle
     */
    public static float rayVsRect(Vec2df ori, Vec2df dir, Rect2df target, Vec2df contactPoint, Vec2df contactNormal) {
        Vec2df near = new Vec2df(target.getPos()).sub(ori).div(dir);
        Vec2df far = new Vec2df(target.getPos()).add(target.getSize()).sub(ori).div(dir);

        if (near.getX() > far.getX()) {
            float v = near.getX();
            near.setX(far.getX());
            far.setX(v);
        }
        if (near.getY() > far.getY()) {
            float v = near.getY();
            near.setY(far.getY());
            far.setY(v);
        }

        // t_near.x > t_far.y || t_near.y > t_far.x
        if (near.getX() > far.getY() || near.getY() > far.getX()) {
            return -1;
        }

        float hitNear = Math.max(near.getX(), near.getY());
        float hitFar = Math.min(far.getX(), far.getY());

        if (hitFar < 0) {
            return -1;
        }

        contactPoint.set(ori);// = new Vec2df(ori);
        Vec2df delta = new Vec2df(dir);
        delta.mul(hitNear);
        contactPoint.add(delta);

        // Vec2df contactNormal = new Vec2df();
        if (near.getX() > near.getY()) {
            if (dir.getX() < 0) {
                contactNormal.set(1, 0);
            } else {
                contactNormal.set(-1, 0);
            }
        } else if (near.getX() < near.getY()) {
            if (dir.getY() < 0) {
                contactNormal.set(0, 1);
            } else {
                contactNormal.set(0, -1);
            }
        }

        // return hitNear < 1;
        return hitNear;
    }

    /**
     * @param rectDynamic the dynamic/moving rect
     * @param rectDynamicDir direction of the dynamic rect
     * @param rectStatic static rect
     * @param contactPoint contact point where the ray intersects with the rectangle
     * @param contactNormal the normal to the side of the rectangle where the contact point is
     * @return true or false if the rectangles are colliding
     *
     * Note: scale the rect dynamic direction based on elapsed time or time step
     */
    /*public static boolean rectVsRect(Rect2df rectDynamic, Vec2df rectDynamicDir, Rect2df rectStatic, Vec2df contactPoint, Vec2df contactNormal) {

        Vec2df halfSizeRectDynamic = new Vec2df(rectDynamic.getSize());
        halfSizeRectDynamic.div(2f);
        Vec2df rectPos = new Vec2df(rectStatic.getPos()).sub(halfSizeRectDynamic);
        Vec2df rectSize = new Vec2df(rectStatic.getSize()).add(rectDynamic.getSize());
        Rect2df expandRect = new Rect2df(rectPos, rectSize);
        Vec2df ori = new Vec2df(rectDynamic.getPos()).add(halfSizeRectDynamic);

        float t = rayVsRect(ori, rectDynamicDir, expandRect, contactPoint, contactNormal);

        return t > -1 && t < 1;
    }*/

    public static float rectVsRect(Rect2df rectDynamic, Vec2df rectDynamicDir, Rect2df rectStatic, Vec2df contactPoint, Vec2df contactNormal) {

        Vec2df halfSizeRectDynamic = new Vec2df(rectDynamic.getSize());
        halfSizeRectDynamic.div(2f);
        Vec2df rectPos = new Vec2df(rectStatic.getPos()).sub(halfSizeRectDynamic);
        Vec2df rectSize = new Vec2df(rectStatic.getSize()).add(rectDynamic.getSize());
        Rect2df expandRect = new Rect2df(rectPos, rectSize);
        Vec2df ori = new Vec2df(rectDynamic.getPos()).add(halfSizeRectDynamic);

        return rayVsRect(ori, rectDynamicDir, expandRect, contactPoint, contactNormal);
    }

    public static float resolveRectVsRect(Rect2df rectDynamic, Vec2df rectDynamicDir, Rect2df rectStatic, Vec2df contactPoint, Vec2df contactNormal) {
        // Vec2df contactPoint = new Vec2df();
        // Vec2df contactNormal = new Vec2df();

        float t = rectVsRect(rectDynamic, rectDynamicDir, rectStatic, contactPoint, contactNormal);
        if (t > -1 && t < 1) {
            Vec2df delta = new Vec2df(contactNormal);
            delta.mulXBy(Math.abs(rectDynamicDir.getX()));
            delta.mulYBy(Math.abs(rectDynamicDir.getY()));
            delta.mul(1 - t);
            rectDynamicDir.add(delta);
        }
        return t;
    }

}
