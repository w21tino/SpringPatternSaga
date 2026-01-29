package com.core.apis.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceCreatedEvent {

    private String paymentId;
    private String orderId;

}
