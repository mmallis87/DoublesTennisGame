package main.uiObject;

import util.Coordinates;

public abstract class Objet3D extends Objet2D {

    public Objet3D() {
        super();
    }

    public Objet3D(Coordinates position, Coordinates vitesse) {
        super(position, vitesse);
    }

    public void setPosition(int x, int y, int z) {
        this.setPosition(x, y);
        this.getPosition().setZ(z);
    }

    public void setSpeed(int x, int y, int z) {
        this.setSpeed(x, y);
        this.getSpeed().setZ(z);

    }
}