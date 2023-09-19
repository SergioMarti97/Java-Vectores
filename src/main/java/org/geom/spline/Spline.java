package org.geom.spline;

import java.util.List;

public abstract class Spline<T> {

    protected List<T> p;

    public List<T> getP() {
        return p;
    }

    public void setP(List<T> p) {
        this.p = p;
    }

}
