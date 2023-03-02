package com.pragma.powerup.factory;

import com.pragma.powerup.domain.order.model.MessageModel;
import com.pragma.powerup.infrastructure.client.model.messaging.Messaging;

public class FactoryMessagingDataTest {

    public static Messaging messaging(){
        return new Messaging(6L,
                "+573214366409",
                "The order is ready");
    }

    public static MessageModel messageModel(){
        return new MessageModel(6L,
                "+573214366409",
                "The order is ready");
    }

}
