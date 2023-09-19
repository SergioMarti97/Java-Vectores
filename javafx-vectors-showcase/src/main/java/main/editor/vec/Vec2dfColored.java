package main.editor.vec;

import javafx.scene.paint.Color;
import org.geom.vector.vec2d.Vec2df;
import org.geom.vector.vec3d.Vec3df;

public class Vec2dfColored extends Vec2df {

    /*
    * Name
    */
    protected String name = "v";

    /**
     * Offset
     */
    protected Vec2df offset = new Vec2df();

    /*
    * Color: r, g, b
    */
    protected Vec3df c = new Vec3df();

    // Constructors

    public Vec2dfColored() {
        super();
        c = new Vec3df();
    }

    public Vec2dfColored(float x, float y) {
        super(x, y);
        c = new Vec3df();
    }

    public Vec2dfColored(float v) {
        super(v);
        c = new Vec3df();
    }

    public Vec2dfColored(Vec2df vec2df) {
        super(vec2df);
        c = new Vec3df();
    }

    public Vec2dfColored(Vec3df c) {
        super();
        this.c = c;
    }

    public Vec2dfColored(float x, float y, Vec3df c) {
        super(x, y);
        this.c = c;
    }

    public Vec2dfColored(float x, float y, float r, float g, float b) {
        super(x, y);
        this.c = new Vec3df(r, g, b);
    }

    public Vec2dfColored(Vec2df vec2df, Vec3df c) {
        super(vec2df);
        this.c = c;
    }

    // -----------------------

    public Vec2dfColored(String name) {
        super();
        this.name = name;
        c = new Vec3df();
    }

    public Vec2dfColored(String name, float x, float y) {
        super(x, y);
        this.name = name;
        c = new Vec3df();
    }

    public Vec2dfColored(String name, float v) {
        super(v);
        this.name = name;
        c = new Vec3df();
    }

    public Vec2dfColored(String name, Vec2df vec2df) {
        super(vec2df);
        this.name = name;
        c = new Vec3df();
    }

    public Vec2dfColored(String name, Vec3df c) {
        super();
        this.name = name;
        this.c = c;
    }

    public Vec2dfColored(String name, float x, float y, Vec3df c) {
        super(x, y);
        this.name = name;
        this.c = c;
    }

    public Vec2dfColored(String name, float x, float y, float r, float g, float b) {
        super(x, y);
        this.name = name;
        this.c = new Vec3df(r, g, b);
    }

    public Vec2dfColored(String name, Vec2df vec2df, Vec3df c) {
        super(vec2df);
        this.name = name;
        this.c = c;
    }

    // Getter and Setter

    public Vec3df getC() {
        return c;
    }

    public Vec2dfColored setC(Vec3df c) {
        this.c.set(c);
        return this;
    }

    public Vec2dfColored setC(float r, float g, float b) {
        this.c.set(r, g, b);
        return this;
    }

    public Vec2df getOffset() {
        return offset;
    }

    public Vec2dfColored setOffset(Vec2df offset) {
        this.offset.set(offset);
        return this;
    }

    public Vec2dfColored setOffset(float x, float y) {
        this.offset.set(x, y);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Make color

    public Color getColor() {
        return Color.color(c.getX(), c.getY(), c.getZ());
    }

    @Override
    public String toString() {
        return name + " " + super.toString() + " " + offset.toString() + " " + c.getX() + "r " + c.getY() + "g " + c.getZ() + "b";
    }

}
