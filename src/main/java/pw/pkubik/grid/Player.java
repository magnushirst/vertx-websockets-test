package pw.pkubik.grid;

class Player {
    final private Vector2d position;

    public Player(final Vector2d position) {
        this.position = position;
    }
    
    public Vector2d getPosition() {
        return position;
    }
    
    public boolean moveUp() {
        position.moveBy(new Vector2d(0, -1));
        return true;
    }
    
    public boolean moveDown() {
        position.moveBy(new Vector2d(0, 1));
        return true;
    }
    
    public boolean moveLeft() {
        position.moveBy(new Vector2d(-1, 0));
        return true;
    }
    
    public boolean moveRight() {
        position.moveBy(new Vector2d(1, 0));
        return true;
    }
}
