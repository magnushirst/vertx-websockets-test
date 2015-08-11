package pw.pkubik.grid;

class Vector2d {
    private int x = 0;
    private int y = 0;
    
    public Vector2d(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void moveTo(final Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }
    
    public void moveBy(final Vector2d vector2d) {
        this.x += vector2d.x;
        this.y += vector2d.y;
    }
}
