package org.geom.shape.rectangle;

import org.geom.shape.Shape;
import org.geom.shape.utils.ShapeOverlapUtils;
import org.geom.vector.vec2d.Vec2df;

public class Rect2df extends Rect<Vec2df> {

    public Rect2df() {
        pos = new Vec2df();
        size = new Vec2df(1);
    }

    public Rect2df(int id) {
        super(id);
        pos = new Vec2df();
        size = new Vec2df(1);
    }

    public Rect2df(int id, Vec2df pos, Vec2df size) {
        super(id, pos, size);
    }

    public Rect2df(Vec2df pos, Vec2df size) {
        super(pos, size);
    }

    public Rect2df(float posX, float posY, float width, float height) {
        setPos(posX, posY);
        setSize(width, height);
    }

    public Rect2df(int id, Vec2df pos, Vec2df size, int zIndex) {
        super(id, pos, size, zIndex);
    }

    public Rect2df(Shape<Vec2df> shape) {
        super(shape);
    }

    @Override
    public void setPos(Vec2df pos) {
        if (this.pos == null) {
            this.pos = new Vec2df(pos);
        } else {
            this.pos.set(pos);
        }
    }

    public void setPos(float x, float y) {
        if (pos == null) {
            pos = new Vec2df();
        }
        pos.set(x, y);
    }

    @Override
    public void setSize(Vec2df size) {
        if (this.size == null) {
            this.size = new Vec2df(size);
        } else {
            this.size.set(size);
        }
    }

    public void setSize(float w, float h) {
        if (size == null) {
            size = new Vec2df();
        }
        size.set(w, h);
    }

    public void set(float x, float y, float w, float h) {
        pos.set(x, y);
        size.set(w, h);
    }

    @Override
    public boolean overlaps(Shape<Vec2df> shape) {
        if (shape instanceof Rect2df) {
            Rect2df r = (Rect2df) shape;
            return ShapeOverlapUtils.rectVsRect(this, r);
        }
        return false;
    }

    @Override
    public boolean contains(Rect<Vec2df> r) {
        return (r.getPos().getX() >= pos.getX()) && (r.getPos().getX() + r.size.getX() < pos.getX() + size.getX()) &&
                (r.getPos().getY() >= pos.getY()) && (r.getPos().getY() + r.size.getY() < pos.getY() + size.getY());
    }

    @Override
    public boolean contains(Vec2df p) {
        return !(p.getX() < pos.getX() || p.getY() < pos.getY() || p.getX() >= (pos.getX() + size.getX()) || p.getY() >= (pos.getY() + size.getY()));
    }

    // Space Division convenient methods

    public float getLeft() {
        return pos.getX();
    }

    public float getTop() {
        return pos.getY();
    }

    public float getRight() {
        return pos.getX() + size.getX();
    }

    public float getBottom() {
        return pos.getY() + size.getY();
    }

    public Vec2df getTopLeft() {
        return new Vec2df(getLeft(), getTop());
    }

    public Vec2df getBottomRight() {
        return new Vec2df(getRight(), getBottom());
    }

    public void setLeft(float left) {
        float diff = left - getLeft();
        size.addToX(-diff);
        pos.setX(left);
    }

    public void setTop(float top) {
        float diff = top - getTop();
        size.addToY(-diff);
        pos.setY(top);
    }

    public void setRight(float right) {
        if (right > getLeft()) {
            float width = right - getLeft();
            size.setX(width);
        } else {
            size.setX(0);
        }
    }

    public void setBottom(float bottom) {
        if (bottom > getTop()) {
            float height = bottom - getTop();
            size.setY(height);
        } else {
            size.setY(0);
        }
    }

}
