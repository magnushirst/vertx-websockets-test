package pw.pkubik.grid;

import java.util.ArrayList;
import java.util.Collections;

class World {
    final private int width;
    final private int height;
    final private ArrayList<Integer> fields;
    
    public World(final int width, final int height) {
        this.width = width;
        this.height = height;
        this.fields = new ArrayList<Integer>(Collections.nCopies(width * height, 0));
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Integer> getFields() {
        return fields;
    }
    
    public int findFieldNumber(final Vector2d position) {
        return position.getX() * position.getY();
    }
}
