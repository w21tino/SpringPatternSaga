package com.core.apis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;
    private String itemType;
    private BigDecimal price;
    private String currency;
    private String orderStatus;

}
