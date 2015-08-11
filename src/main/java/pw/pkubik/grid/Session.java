package pw.pkubik.grid;

import io.vertx.core.Vertx;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.core.json.JsonObject;

abstract class Session {
    protected final Vertx vertx;
    protected final ServerWebSocket socket;
    protected final long clientId;
    private final long timerId;
    
    public Session(Vertx vertx, ServerWebSocket socket, long clientId) {
        this.vertx = vertx;
        this.socket = socket;
        this.clientId = clientId;
        this.timerId = vertx.setPeriodic(200, id -> periodicEvent());
        
        initConnection();
        setWebSocketHandlers();
    }
    
    protected abstract void periodicEvent();
    
    private void initConnection() {
        System.out.println("#" + Long.toString(clientId) + " connected!");
    }
    
    private void setWebSocketHandlers() {
        socket.frameHandler(msg -> {
            handleFrame(msg);
        });
        
        socket.closeHandler( (Void) -> {
            cancelPeriodicEvent();
            close();
        });
    }
    
    private void cancelPeriodicEvent() {
        vertx.cancelTimer(timerId);
    }

    public void send(JsonObject jsonObject) {
        socket.writeFinalTextFrame(jsonObject.encode());
    }
    
    protected void handleFrame(WebSocketFrame msg) {
        System.out.println(msg.textData());
    }
    
    protected void close() {
        System.out.println("#" + Long.toString(clientId) + " disconnected");
    }
}
