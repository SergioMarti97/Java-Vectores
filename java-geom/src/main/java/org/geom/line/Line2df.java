package org.geom.line;

import org.geom.vector.vec2d.Vec2df;

public class Line2df {
    
    private Vec2df ori;
    
    private Vec2df dir;

    public Line2df(Vec2df ori, Vec2df dir) {
        setOri(ori);
        setDir(dir);
    }

    public Vec2df getOri() {
        return ori;
    }

    public void setOri(Vec2df ori) {
        if (this.ori == null) {
            this.ori = new Vec2df(ori);
        } else {
            this.ori.set(ori);
        }
    }

    public Vec2df getDir() {
        return dir;
    }

    public void setDir(Vec2df dir) {
        if (this.dir == null) {
            this.dir = new Vec2df(dir);
        } else {
            this.dir.set(dir);
        }
    }

    public void set(Vec2df ori, Vec2df dir) {
        setOri(ori);
        setDir(dir);
    }

}
