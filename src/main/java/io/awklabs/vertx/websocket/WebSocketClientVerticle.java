package io.awklabs.vertx.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WebSocketClientVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    startClient(vertx);
  }

  private void startClient(Vertx vertx) {
    HttpClient client = vertx.createHttpClient();

    client.webSocket(8080, "localhost", "/", result -> result.map((Function<WebSocket, Object>) ctx -> ctx.textMessageHandler((msg) -> {
      System.out.println("Client " + msg);
      ctx.writeTextMessage("pong");
    }).exceptionHandler((e) -> {
      System.out.println("Closed, restarting in 10 seconds");
      restart(client, 5);
    }).closeHandler((__) -> {
      System.out.println("Closed, restarting in 10 seconds");
      restart(client, 10);
    })));
  }

  private void restart(HttpClient client, int delay) {
    client.close();
    vertx.setTimer(TimeUnit.SECONDS.toMillis(delay), (__) -> startClient(vertx));
  }
}
