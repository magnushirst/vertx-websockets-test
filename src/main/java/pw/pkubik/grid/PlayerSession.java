package pw.pkubik.grid;

import java.util.List;
import java.util.Map.Entry;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

class PlayerSession extends Session {
    private final Game game;
    private final Player player;

    public PlayerSession(Vertx vertx, List<Session> sessions, ServerWebSocket socket, long clientId, Game game) {
        super(vertx, sessions, socket, clientId);
        this.game = game;
        this.player = game.getPlayers().get(clientId);
        
        JsonObject jsonObject = new JsonObject()
                .put("fields", new JsonArray(game.getWorld().getFields()))
                .put("playerId", clientId);
        send(jsonObject);
        sendToAll(playersStateJson());
    }

    @Override
    protected void periodicEvent() {
        send(playersStateJson());
    }
    
    @Override
    protected void handleFrame(WebSocketFrame msg) {
        super.handleFrame(msg);
        JsonObject jsonObject = new JsonObject(msg.textData());
        
        String action = jsonObject.getString("action");
        if (action != null) {
            switch (action) {
            case "up":
                player.moveUp(); break;
            case "down":
                player.moveDown(); break;
            case "left":
                player.moveLeft(); break;
            case "right":
                player.moveRight(); break;
            }
        }
        
        sendToAll(playersStateJson());
    }
    
    private JsonObject playersStateJson() {
        JsonArray jsonArray = new JsonArray();
        for (Entry<Long, Player> entry : game.getPlayers().entrySet()) {
            jsonArray.add(new JsonObject()
                    .put("id", entry.getKey())
                    .put("x", entry.getValue().getPosition().getX())
                    .put("y", entry.getValue().getPosition().getY())
                    );
        }
        return new JsonObject().put("players", jsonArray);
    }

    @Override
    protected void close() {
        super.close();
        game.removePlayer(clientId);
    }
}
