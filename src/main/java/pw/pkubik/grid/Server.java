package pw.pkubik.grid;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
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

        server.websocketHandler(new Handler<ServerWebSocket>() {

            @Override
            public void handle(ServerWebSocket sws) {
                new PlayerSession(vertx, sws, game.createPlayer(), game);
            }
        });

        server.listen(8080);
    }
}
