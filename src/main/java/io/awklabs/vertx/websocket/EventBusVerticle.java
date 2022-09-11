package io.awklabs.vertx.websocket;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.BridgeOptions;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.eventbus.bridge.tcp.TcpEventBusBridge;

import java.util.UUID;

public class EventBusVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    startEventBusBridge(vertx);
    vertx.createHttpServer().requestHandler(req -> {
      String commandID = UUID.randomUUID().toString();
      vertx.eventBus().consumer("response:"+commandID, message -> {
        System.out.println("Message received " + message.body().toString());
        if(!req.response().ended()) {
          req.response().end(message.body().toString());
        }
      });
      vertx.eventBus().publish("command", commandID);
    }).listen(8080);
  }

  private void startEventBusBridge(Vertx vertx) {
    BridgeOptions options = new BridgeOptions()
      .addInboundPermitted(new PermittedOptions().setAddressRegex("response:.*"))
      .addOutboundPermitted(new PermittedOptions().setAddressRegex("response:.*"));

    TcpEventBusBridge.create(vertx, options).listen(7000, response -> {
      if(response.succeeded()) {
        System.out.println("Success in connecting!!");
      } else {
        System.out.println("Failed!!!");
      }
    });
  }
}
