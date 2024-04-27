package com.user.info.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
   private String phoneNumber;
   private String orderNumber;
   private String productCode;
}
