package com.epam.rabbitmq.domain;

import com.epam.rabbitmq.domain.Product;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class Receipt implements Serializable {
  List<Product> products;
}
