package pw.pkubik.grid;

import java.util.Map.Entry;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class PlayerSession extends Session {
    private final Game game;

    public PlayerSession(Vertx vertx, ServerWebSocket socket, long clientId, Game game) {
        super(vertx, socket, clientId);
        this.game = game;
        
        JsonObject jsonObject = new JsonObject()
                .put("fields", new JsonArray(game.getWorld().getFields()))
                .put("playerId", clientId);
        send(jsonObject);
    }

    @Override
    protected void periodicEvent() {
        JsonArray jsonArray = new JsonArray();
        for (Entry<Long, Player> entry : game.getPlayers().entrySet()) {
            jsonArray.add(new JsonObject()
                    .put("id", entry.getKey())
                    .put("x", entry.getValue().getPosition().getX())
                    .put("y", entry.getValue().getPosition().getY())
                    );
        }
        send(new JsonObject().put("players", jsonArray));
    }

    @Override
    protected void close() {
        super.close();
        game.removePlayer(clientId);
    }
}
