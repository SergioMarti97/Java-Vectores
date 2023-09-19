package org.geom.vector.vec2d;

import java.util.Objects;

public class Vec2df implements Vec2d, Comparable<Vec2df> {

    /**
     * x component
     */
    protected float x;

    /**
     * y component
     */
    protected float y;

    /**
     * Void constructor
     */
    public Vec2df() {
        this.x = 0.0f;
        this.y = 0.0f;
    }

    /**
     * Constructor
     * @param x the value of x component
     * @param y the value of y component
     */
    public Vec2df(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor with same value for x and y
     * @param v value for x and y
     */
    public Vec2df(float v) {
        this.x = v;
        this.y = v;
    }

    /**
     * Copy constructor
     * @param vec2df the instance of the same object to copy
     */
    public Vec2df(Vec2df vec2df) {
        this.x = vec2df.getX();
        this.y = vec2df.getY();
    }

    // ************** //
    // Add operations //
    // ************** //

    /**
     * This method add to the components the amount
     * pass as a parameter
     * @param amount the amount to add to both components
     */
    public Vec2df add(float amount) {
        x += amount;
        y += amount;
        return this;
    }

    /**
     * This method add to the x component the amount
     * pass as a parameter
     * @param amount the amount to add to the x component
     */
    public Vec2df addToX(float amount) {
        x += amount;
        return this;
    }

    /**
     * This method add to the y component the amount
     * pass as a parameter
     * @param amount the amount to add to the y component
     */
    public Vec2df addToY(float amount) {
        y += amount;
        return this;
    }

    /**
     * This method add to each component a different amount
     * passed by parameter to x and y coordinates
     * @param x the amount to add to x
     * @param y the amount to add to y
     */
    public Vec2df add(float x, float y) {
        addToX(x);
        addToY(y);
        return this;
    }

    // ********************** //
    // Subtraction operations //
    // ********************** //

    public Vec2df sub(float amount) {
        x -= amount;
        y -= amount;
        return this;
    }

    public Vec2df subToX(float amount) {
        x -= amount;
        return this;
    }

    public Vec2df subToY(float amount) {
        y -= amount;
        return this;
    }

    public Vec2df sub(float x, float y) {
        subToX(x);
        subToY(y);
        return this;
    }

    // ******************* //
    // Multiply operations //
    // ******************* //

    /**
     * This method multiply the two components by the amount
     * pass as a parameter
     * @param amount the amount to multiply both components
     */
    public Vec2df mul(float amount) {
        x *= amount;
        y *= amount;
        return this;
    }

    /**
     * This method multiply the x component by the amount pass
     * as a parameter
     * @param amount the amount to multiply the x component
     */
    public Vec2df mulXBy(float amount) {
        x *= amount;
        return this;
    }

    /**
     * This method multiply the y component by the amount pass
     * as a parameter
     * @param amount the amount to multiply the y component
     */
    public Vec2df mulYBy(float amount) {
        y *= amount;
        return this;
    }

    public Vec2df mul(float x, float y) {
        mulXBy(x);
        mulYBy(y);
        return this;
    }

    // ******************* //
    // Division operations //
    // ******************* //

    public Vec2df div(float amount) {
        x /= amount;
        y /= amount;
        return this;
    }

    public Vec2df divXBy(float amount) {
        x /= amount;
        return this;
    }

    public Vec2df divYBy(float amount) {
        y /= amount;
        return this;
    }

    public Vec2df div(float x, float y) {
        divXBy(x);
        divYBy(y);
        return this;
    }

    /**
     * Getter for the x component
     * @return the x component
     */
    public float getX() {
        return x;
    }

    /**
     * Getter for the y component
     * @return the y component
     */
    public float getY() {
        return y;
    }

    /**
     * Setter for the x component
     * @param x the new value for the x component
     */
    public Vec2df setX(float x) {
        this.x = x;
        return this;
    }

    /**
     * Setter for the y component
     * @param y the new value for the y component
     */
    public Vec2df setY(float y) {
        this.y = y;
        return this;
    }

    /**
     * Setter for the two components
     * @param x new value for x
     * @param y new value for y
     */
    public Vec2df set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2df set(float v) {
        this.x = v;
        this.y = v;
        return this;
    }

    /**
     * This method returns the magnitude of the vector.
     * It uses the pythagorean theorem to calculate
     * the module of the vector.
     *
     * h^2 = a^2 + b^2
     *
     * @return the magnitude of the vector
     */
    public float mag() {
        return (float)(Math.sqrt(mag2()));
    }

    /**
     * This method returns the magnitude of the vector
     * without make the root square. It uses the
     * pythagorean theorem to calculate the module
     * of the vector.
     *
     * (h * h) = (a * a) + (b * b)
     *
     * As the root square is a potential high cost
     * operation, is interesting have this method.
     *
     * @return two times the magnitude of the vector
     */
    public float mag2() {
        return (x * x) + (y * y);
    }

    public float dist(float x, float y) {
        return (float)Math.sqrt(dist2(x, y));
    }

    public float dist2(float x, float y) {
        float a = this.x - x;
        float b = this.y - y;
        return (a * a) + (b * b);
    }

    public float dist(Vec2df pos) {
        return (float)Math.sqrt(dist2(pos));
    }

    public float dist2(Vec2df pos) {
        float a = this.x - pos.getX();
        float b = this.y - pos.getY();
        return (a * a) + (b * b);
    }

    @Override
    public void set(Vec2d vec2d) {
        if ( vec2d instanceof Vec2df) {
            Vec2df vec2df = (Vec2df)(vec2d);
            setX(vec2df.getX());
            setY(vec2df.getY());
        } else if ( vec2d instanceof Vec2di ) {
            Vec2di vec2di = (Vec2di)(vec2d);
            setX((float)vec2di.getX());
            setY((float)vec2di.getY());
        } else if ( vec2d instanceof Vec2dd) {
            Vec2dd vec2dd = (Vec2dd)(vec2d);
            setX((float)(vec2dd.getX()));
            setY((float)(vec2dd.getY()));
        } else {
            this.x = 0;
            this.y = 0;
        }
    }

    @Override
    public void add(Vec2d vec2d) {
        if ( vec2d instanceof Vec2df) {
            Vec2df vec2df = (Vec2df)(vec2d);
            this.x += vec2df.getX();
            this.y += vec2df.getY();
        }
    }

    public Vec2df add(Vec2df vec2df) {
        this.x += vec2df.getX();
        this.y += vec2df.getY();
        return this;
    }

    @Override
    public void sub(Vec2d vec2d) {
        if ( vec2d instanceof Vec2df) {
            Vec2df vec2df = (Vec2df)(vec2d);
            this.x -= vec2df.getX();
            this.y -= vec2df.getY();
        }
    }

    public Vec2df sub(Vec2df vec2df) {
        this.x -= vec2df.getX();
        this.y -= vec2df.getY();
        return this;
    }

    @Override
    public void mul(Vec2d vec2d) {
        if ( vec2d instanceof Vec2df) {
            Vec2df vec2df = (Vec2df)(vec2d);
            this.x *= vec2df.getX();
            this.y *= vec2df.getY();
        }
    }

    public Vec2df mul(Vec2df vec2df) {
        this.x *= vec2df.getX();
        this.y *= vec2df.getY();
        return this;
    }

    @Override
    public void div(Vec2d vec2d) {
        if ( vec2d instanceof Vec2df) {
            Vec2df vec2df = (Vec2df)(vec2d);
            this.x /= vec2df.getX();
            this.y /= vec2df.getY();
        }
    }

    public Vec2df div(Vec2df vec2df) {
        this.x /= vec2df.getX();
        this.y /= vec2df.getY();
        return this;
    }

    @Override
    public void normalize() {
        float l = mag();
        this.x /= l;
        this.y /= l;
    }

    @Override
    public Vec2d normalized() {
        float r = 1 / mag();
        return new Vec2df(x * r,y * r);
    }

    public Vec2df norm(){
        normalize();
        return this;
    }

    @Override
    public Vec2d perpendicular() {
        return new Vec2df(-y, x);
    }

    public Vec2df perp() {
        this.x = -y;
        this.y = x;
        return this;
    }

    @Override
    public void translateThisAngle(float angle) {
        angle *= (Math.PI / 180.0f);
        float x = (float)((this.x * Math.cos(angle)) - (this.y * Math.sin(angle)));
        float y = (float)((this.x * Math.sin(angle)) + (this.y * Math.cos(angle)));
        this.x = x;
        this.y = y;
    }

    public Vec2df negative() {
        return new Vec2df(-x, -y);
    }

    public Vec2df abs() {
        return new Vec2df(Math.abs(x), Math.abs(y));
    }

    public Vec2df absCords() {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        return this;
    }

    public Vec2df floor() {
        return new Vec2df((float)Math.floor(x), (float)Math.floor(y));
    }

    public Vec2df floorCords() {
        this.x = (float)Math.floor(x);
        this.y = (float)Math.floor(y);
        return this;
    }

    public Vec2df ceil() {
        return new Vec2df((float)Math.ceil(x), (float)Math.ceil(y));
    }

    public Vec2df ceilCords() {
        this.x = (float)Math.ceil(x);
        this.y = (float)Math.ceil(y);
        return this;
    }

    public Vec2df max(Vec2df v) {
        return new Vec2df(Math.max(this.x, v.x), Math.max(this.y, v.y));
    }

    public Vec2df min(Vec2df v) {
        return new Vec2df(Math.min(this.x, v.x), Math.min(this.y, v.y));
    }

    public Vec2df clamp(Vec2df min, Vec2df max) {
        return this.max(min).min(max);
    }

    public Vec2df cart() {
        return new Vec2df((float)Math.cos(y) * x, (float)Math.sin(y) * x);
    }

    public Vec2df cartCords() {
        this.x = (float)Math.cos(y) * x;
        this.y = (float)Math.sin(y) * x;
        return this;
    }

    public Vec2df polar() {
        return new Vec2df(mag(), (float) Math.atan2(y, x));
    }

    public Vec2df polarCords() {
        this.x = mag();
        this.y = (float) Math.atan2(y, x);
        return this;
    }

    public float lerpX(float x, float t) {
        return this.x * (1 - t) + x * t;
    }

    public float lerpY(float y, float t) {
        return this.y * (1 - t) + y * t;
    }

    public Vec2df lerp(Vec2df v, float t) {
        return new Vec2df(
                x * (1 - t) + v.x * t,
                y * (1 - t) + v.y * t
        );
    }

    public Vec2df lerpCords(Vec2df v, float t) {
        this.x = this.x * (1 - t) + v.x * t;
        this.y = this.y * (1 - t) + v.y * t;
        return this;
    }

    public Vec2df medVec(Vec2df v) {
        return lerp(v, 0.5f);
    }

    public Vec2df medVecCord(Vec2df v) {
        return lerpCords(v, 0.5f);
    }

    public boolean isParallel(Vec2df v) {
        return (this.x / v.x) == (this.y / v.y);
    }

    public boolean isPerp(Vec2df v) {
        return this.dotProduct(v) == 0;
    }

    public float angle(Vec2df v) {
        return (float) Math.acos(this.dotProduct(v) / (this.mag() * v.mag()));
    }

    public float projection(Vec2df v) {
        return Math.abs(v.dotProduct(this)) / this.mag();
    }

    /**
     * The dot product is a way to measure how similar are
     * two vectors.
     * @return an amount that represents the similarity.
     */
    public float dotProduct(Vec2df vec) {
        return x * vec.getX() + y * vec.getY();
    }

    /**
     * The cross product I don't know what is at all but is
     * the opposite of the dot product.
     * @return an amount.
     */
    public float crossProduct(Vec2df vec) {
        return x * vec.getY() - y * vec.getX();
    }

    @Override
    public String toString() {
        return x + "x " + y + "y";
    }

    @Override
    public int compareTo(Vec2df v) {
        int r = Float.compare(x, v.getX());
        if (r == 0) {
            return Float.compare(y, v.getY());
        } else {
            return r;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec2df)) return false;
        Vec2df vec2df = (Vec2df) o;
        return Float.compare(vec2df.getX(), getX()) == 0 && Float.compare(vec2df.getY(), getY()) == 0;
    }

    public boolean lessThan(Vec2df v) {
        return this.y < v.y || (this.y == v.y && this.x < v.x);
    }

    public boolean greaterThan(Vec2df v) {
        return this.y > v.y || (this.y == v.y && this.x > v.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public static Vec2df medVec(Vec2df v1, Vec2df v2) {
        return new Vec2df(
                (v1.x + v2.x) / 2f,
                (v1.y + v2.y) / 2f
        );
    }

}
