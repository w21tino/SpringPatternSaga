package com.core.apis.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateInvoiceCommand {

    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;

}
