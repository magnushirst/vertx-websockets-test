package pw.pkubik.grid;

import java.util.HashMap;
import java.util.Map;

class Game {
    private final World world = new World(32, 25);
    private final Map<Long, Player> players = new HashMap<Long, Player>();
    private long idGen = 0;
    
    public long createPlayer() {
        final int x = (int) Math.floor(Math.random() * world.getWidth());
        final int y = (int) Math.floor(Math.random() * world.getHeight());
        players.put(idGen, new Player(new Vector2d(x, y)));
        return idGen++;
    }

    public World getWorld() {
        return world;
    }

    public Map<Long, Player> getPlayers() {
        return players;
    }
    
    public void removePlayer(long id) {
        players.remove(id);
    }
}
