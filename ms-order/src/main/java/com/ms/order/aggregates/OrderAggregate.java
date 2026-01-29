package com.ms.order.aggregates;

import com.core.apis.commands.CreateOrderCommand;
import com.core.apis.commands.UpdateOrderStatusCommand;
import com.core.apis.events.OrderCreatedEvent;
import com.core.apis.events.OrderUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.getOrderId(),createOrderCommand.getItemType(),createOrderCommand.getPrice(),createOrderCommand.getCurrency(),createOrderCommand.getOrderStatus()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.getOrderId();
        this.itemType = ItemType.valueOf(orderCreatedEvent.getItemType());
        this.price = orderCreatedEvent.getPrice();
        this.currency = orderCreatedEvent.getCurrency();
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.getOrderStatus());
    }

    @CommandHandler
    public void on(UpdateOrderStatusCommand updateOrderStatusCommand){
        AggregateLifecycle.apply(new OrderUpdateEvent(updateOrderStatusCommand.getOrderId(),updateOrderStatusCommand.getOrderStatus()));
    }

    @EventSourcingHandler
    public void on(OrderUpdateEvent orderUpdateEvent){
        this.orderId = orderUpdateEvent.getOrderId();
        this.orderStatus = OrderStatus.valueOf(orderUpdateEvent.getOrderStatus());
    }
}
