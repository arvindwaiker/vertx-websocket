package io.awklabs.vertx.websocket;

import io.vertx.core.json.JsonObject;
import io.vertx.eventbusclient.EventBusClient;
import io.vertx.eventbusclient.EventBusClientOptions;

public class EventBusClientMain {
  public static void main(String[] args) {
    EventBusClientOptions options = new EventBusClientOptions().setHost("localhost").setPort(7000);
    EventBusClient client = EventBusClient.tcp(options);

    JsonObject message = JsonObject.mapFrom(new Message("My Second Message"));

    client.send("response:de229a7b-5769-4437-bbb8-b1610d80346b", message);
    client.close();

  }
}
