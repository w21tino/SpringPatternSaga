package com.ms.payment.aggregates;

import com.core.apis.commands.CreateInvoiceCommand;
import com.core.apis.events.InvoiceCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class InvoiceAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) {
        AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.getPaymentId(),createInvoiceCommand.getOrderId()));
    }

    @EventSourcingHandler
    public void on(InvoiceCreatedEvent invoiceCreatedEvent){
        this.paymentId = invoiceCreatedEvent.getPaymentId();
        this.orderId = invoiceCreatedEvent.getOrderId();
        this.invoiceStatus = InvoiceStatus.PAID;
    }
}
