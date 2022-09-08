package com.epam.rabbitmq.domain;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Product implements Serializable {
  String name;
  String price;
}
