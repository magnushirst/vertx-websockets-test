package pw.pkubik.grid;

import java.util.LinkedList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.impl.StaticHandlerImpl;

public class Server extends AbstractVerticle {
    private final Game game = new Game();

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route("/").handler(routingContext -> {
            routingContext.response().putHeader("content-type", "text/html").sendFile("webroot/index.html");
        });

        router.route("/static/*").handler(new StaticHandlerImpl());

        HttpServer server = vertx.createHttpServer().requestHandler(router::accept);
        
        List<Session> sessions = new LinkedList<Session>();

        server.websocketHandler( socket ->
            sessions.add(new PlayerSession(vertx, sessions, socket, game.createPlayer(), game)));

        server.listen(8080);
    }
}
