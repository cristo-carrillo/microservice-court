package com.pragma.powerup.infrastructure.client.model.messaging;

import com.pragma.powerup.domain.order.model.MessageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IMessageMapper {
    Messaging toMessage(MessageModel messageModel);
}
