package org.geom.shape;

import org.geom.shape.rectangle.Rect;

import java.util.Objects;

public abstract class Shape<T> {

    protected int id = -1;

    protected int zIndex = 0;

    protected T pos;

    public Shape() {
    }

    public Shape(int id) {
        this.id = id;
    }

    public Shape(T pos) {
        setPos(pos);
    }

    public Shape(int id, T pos) {
        this.id = id;
        setPos(pos);
    }

    public Shape(int id, T pos, int zIndex) {
        this.id = id;
        setPos(pos);
        this.zIndex = zIndex;
    }

    public Shape(Shape<T> shape) {
        this.id = shape.id;
        this.zIndex = shape.zIndex;
        setPos(shape.pos);
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public int getzIndex() {
        return zIndex;
    }

    public T getPos() {
        return pos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public abstract void setPos(T pos);

    public abstract boolean overlaps(Shape<T> shape);

    public abstract boolean contains(Rect<T> r);

    public abstract boolean contains(T p);

    @Override
    public String toString() {
        return "id: " + id + " pos: " + pos.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shape)) return false;
        Shape<?> shape = (Shape<?>) o;
        return getId() == shape.getId() && getzIndex() == shape.getzIndex() && getPos().equals(shape.getPos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getzIndex(), getPos());
    }

}
