package io.awklabs.vertx.websocket;

import io.vertx.core.AbstractVerticle;

public class TestVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("command", msg -> {
      var commandID = msg.body().toString();
      vertx.eventBus().publish("response:"+commandID, "Message("+commandID+")");
    });
  }
}
