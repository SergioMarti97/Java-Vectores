package org.geom.space.division;

import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;

import java.util.ArrayList;
import java.util.List;

public class QuadTree2df<T> {

    private final int NUM_CHILDREN = 4;

    private final int MAX_DEPTH = 10;

    private Rect2df rect;

    private Rect2df[] rectChildren = new Rect2df[NUM_CHILDREN];

    private QuadTree2df<T>[] children = new QuadTree2df[NUM_CHILDREN];

    private final List<Pair<Rect2df, T>> items = new ArrayList<>();

    private int depth = 0;

    public QuadTree2df(Rect2df rect, int depth) {
        this.depth = depth;
        resize(rect);
    }

    public QuadTree2df(Rect2df rect) {
        this(rect, 0);
    }

    public QuadTree2df() {
        this(new Rect2df(0, 0, 100, 100));
    }

    // Methods

    public void resize(Rect2df area) {
        clear();
        rect = area;

        Vec2df childSize = new Vec2df(rect.getSize());
        childSize.mul(0.5f);

        rectChildren[0] = new Rect2df(rect.getPos().getX(), rect.getPos().getY(), childSize.getX(), childSize.getY());
        rectChildren[1] = new Rect2df(rect.getPos().getX() + childSize.getX(), rect.getPos().getY(), childSize.getX(), childSize.getY());
        rectChildren[2] = new Rect2df(rect.getPos().getX(), rect.getPos().getY() + childSize.getY(), childSize.getX(), childSize.getY());
        rectChildren[3] = new Rect2df(rect.getPos().getX() + childSize.getX(), rect.getPos().getY() + childSize.getY(), childSize.getX(), childSize.getY());
    }

    public void clear() {
        items.clear();

        for (int i = 0; i < NUM_CHILDREN; i++) {
            if (children[i] != null) {
                children[i].clear();
            }
            children[i] = null;
        }
    }

    public int size() {
        int count = items.size();
        for (var child : children) {
            if (child != null) {
                count += child.size();
            }
        }
        return count;
    }

    public void insert(T item, Rect2df itemArea) {
        // Comprobar si el objeto a insertar encaja en alguna de las 4 áreas hijas
        for (int i = 0; i < NUM_CHILDREN; i++) {
            try {
                if (rectChildren[i].contains(itemArea)) {
                    if (depth + 1 < MAX_DEPTH) {

                        if (children[i] == null) {
                            children[i] = new QuadTree2df<T>(rectChildren[i], depth + 1);
                        }

                        children[i].insert(item, itemArea);
                        return;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }

        items.add(new Pair<>(itemArea, item));
    }

    public List<T> search(Rect2df area) {
        ArrayList<T> listItems = new ArrayList<>();
        search(area, listItems);
        return listItems;
    }

    private void search(Rect2df area, List<T> listItems) {
        for (var item : items) {
            if (area.overlaps(item.getKey())) {
                listItems.add(item.getValue());
            }
        }

        for (int i = 0; i < NUM_CHILDREN; i++) {
            if (children[i] != null) {
                if (area.contains(rectChildren[i])) {
                    children[i].items(listItems);
                } else if (rectChildren[i].overlaps(area)) {
                    children[i].search(area, listItems);
                }
            }
        }
    }

    private void items(List<T> list) {
        for (var item : items) {
            list.add(item.getValue());
        }

        for (int i = 0; i < NUM_CHILDREN; i++) {
            if (children[i] != null) {
                children[i].items(list);
            }
        }
    }

    public List<T> items() {
        ArrayList<T> list = new ArrayList<>();
        items(list);
        return list;
    }

    public boolean hasChildren() {
        for (int i = 0; i < NUM_CHILDREN; i++) {
            if (children[i] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        if (!hasChildren()) {
            return items.isEmpty();
        } else {
            boolean isEmpty = items.isEmpty();
            if (isEmpty) {

                for (int i = 0; i < NUM_CHILDREN; i++) {
                    if (children[i] != null) {
                        if (!children[i].isEmpty()) {
                            return false;
                        }
                    }
                }
                return true;

            } else {
                return false;
            }
        }
    }

    /**
     * Para que funcione eficientemente la eliminación recursiva,
     * al algoritmo se le debe de pasar 2 cosas: el elemento y su ubicación (área).
     * Comprobar si un elemento hijo tiene o no items. Si no tiene, se debe eliminar.
     * @param item the item to remove
     * @param itemArea the location of the item
     * @return true or false if the item is removed
     */
    public boolean remove(T item, Rect2df itemArea) {
        boolean isHere = items.removeIf(p -> p.getValue() == item);
        if (!isHere) {
            for (int i = 0; i < NUM_CHILDREN; i++) {
                if (children[i] != null) {

                    if (rectChildren[i].contains(itemArea)) {
                        boolean isRemoved = children[i].remove(item, itemArea);

                        if (children[i].isEmpty()) {
                            children[i] = null;
                        }

                        return isRemoved;
                    }

                }
            }
            return false;
        } else {
            return isHere;
        }
    }

    // Getters & Setters

    public Rect2df getRect() {
        return rect;
    }

    public Rect2df[] getRectChildren() {
        return rectChildren;
    }

    public QuadTree2df<T>[] getChildren() {
        return children;
    }

    public List<Pair<Rect2df, T>> getItems() {
        return items;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "QuadTree {children: " + children.length + "}";
    }
    
}
