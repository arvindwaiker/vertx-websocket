package io.awklabs.vertx.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

import java.util.Random;

public class WebSocketServerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    startServer(vertx);
  }

  private void startServer(Vertx vertx) {
    HttpServer server = vertx.createHttpServer();
    server.webSocketHandler(ctx -> {
      ctx.writeTextMessage("Ping");
      ctx.textMessageHandler(message -> {
        System.out.println("Server " + message);
        ctx.writeTextMessage("Ping");
      });
    }).listen(8080);
  }
}
