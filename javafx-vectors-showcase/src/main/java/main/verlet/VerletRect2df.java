package main.verlet;

import org.geom.shape.Shape;
import org.geom.shape.rectangle.Rect2df;
import org.geom.vector.vec2d.Vec2df;

public class VerletRect2df extends Rect2df implements IVerlet {

    protected float mass;

    /**
     * Old position
     */
    protected Vec2df oldPos = new Vec2df();

    /**
     * Acceleration
     */
    protected Vec2df acc = new Vec2df();

    // Constructor

    public VerletRect2df() {
        super();
    }

    public VerletRect2df(int id) {
        super(id);
    }

    public VerletRect2df(int id, Vec2df pos, Vec2df size) {
        super(id, pos, size);
        this.oldPos.set(pos);
    }

    public VerletRect2df(Vec2df pos, Vec2df size) {
        super(pos, size);
        this.oldPos.set(pos);
    }

    public VerletRect2df(float posX, float posY, float width, float height) {
        super(posX, posY, width, height);
        this.oldPos.set(pos);
    }

    public VerletRect2df(int id, Vec2df pos, Vec2df size, int zIndex) {
        super(id, pos, size, zIndex);
        this.oldPos.set(pos);
    }

    public VerletRect2df(Shape<Vec2df> shape) {
        super(shape);
    }

    // Getters & Setters

    public Vec2df getOldPos() {
        return oldPos;
    }

    public VerletRect2df setOldPos(Vec2df oldPos) {
        if (this.oldPos == null) {
            this.oldPos = new Vec2df();
        }
        this.oldPos.set(oldPos);
        return this;
    }

    public Vec2df getAcc() {
        return acc;
    }

    public VerletRect2df setAcc(Vec2df acc) {
        if (this.acc == null) {
            this.acc = new Vec2df();
        }
        this.acc.set(acc);
        return this;
    }

    public float getMass() {
        return mass;
    }

    public VerletRect2df setMass(float mass) {
        this.mass = mass;
        return this;
    }

    // Override methods

    @Override
    public void doVerletStep(float dt) {
        // Calculate velocity
        Vec2df vel = calVelocity();

        // Save current position
        oldPos.set(pos);

        // Perform Verlet integration
        Vec2df acc = new Vec2df(this.acc);
        acc.mul(dt * dt);
        pos.add(vel).add(acc);

        // Reset acceleration
        this.acc.set(0);
    }

    @Override
    public void accelerate(Vec2df acc) {
        this.acc.add(acc);
    }

    public void applyForce(Vec2df f) {
        // F = m * a --> a = F / m
        accelerate(new Vec2df(f).div(mass));
    }

    public Vec2df calVelocity() {
        // Calculate velocity
        Vec2df vel = new Vec2df(pos);
        vel.sub(oldPos);

        final float minVel = 0.01f;
        if (Math.abs(vel.getX()) < minVel) {
            vel.setX(0);
        }
        if (Math.abs(vel.getY()) < minVel) {
            vel.setY(0);
        }
        return vel;
    }

}
