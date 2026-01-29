package com.core.apis.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderUpdateEvent {

    private String orderId;
    private String orderStatus;

}

