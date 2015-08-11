package pw.pkubik.grid;

class Player {
    final private Vector2d position;

    public Player(final Vector2d position) {
        this.position = position;
    }
    
    public Vector2d getPosition() {
        return position;
    }
}
