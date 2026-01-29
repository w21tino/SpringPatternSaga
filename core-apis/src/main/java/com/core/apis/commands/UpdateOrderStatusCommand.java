package com.core.apis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus;

}
