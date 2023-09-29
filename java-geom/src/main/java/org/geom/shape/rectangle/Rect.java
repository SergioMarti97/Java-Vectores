package org.geom.shape.rectangle;

import org.geom.shape.Shape;

public abstract class Rect<T> extends Shape<T> {

    protected T size;

    public Rect() {
    }

    public Rect(int id) {
        super(id);
    }

    public Rect(int id, T pos, T size) {
        super(id, pos);
        setSize(size);
    }

    public Rect(T pos, T size) {
        super(pos);
        setSize(size);
    }

    public Rect(int id, T pos, T size, int zIndex) {
        super(id, pos, zIndex);
        setSize(size);
    }

    public Rect(Shape<T> shape) {
        super(shape);
        if (shape instanceof Rect) {
            Rect<T> r = (Rect<T>) shape;
            setSize(r.size);
        }
    }

    public T getSize() {
        return size;
    }

    public abstract void setSize(T size);

    public void set(T pos, T size) {
        setPos(pos);
        setSize(size);
    }

    @Override
    public String toString() {
        return "id: " + id + " pos: " + pos + " size: " + size;
    }

}
