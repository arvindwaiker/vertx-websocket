package io.awklabs.vertx.websocket;

import io.vertx.core.Vertx;

public class Main {
  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new WebSocketServerVerticle(), (__) -> vertx.deployVerticle(new WebSocketClientVerticle()));
  }
}
