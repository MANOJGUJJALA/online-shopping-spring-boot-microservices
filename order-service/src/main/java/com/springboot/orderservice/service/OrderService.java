package com.springboot.orderservice.service;


import com.springboot.orderservice.dto.InventoryResponse;
import com.springboot.orderservice.dto.OrderLineItemsDto;
import com.springboot.orderservice.dto.OrderRequest;
import com.springboot.orderservice.model.Order;
import com.springboot.orderservice.model.OrderLineItems;
import com.springboot.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public String  placeOrder(OrderRequest orderRequest){
        Order order=new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

      List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtoList()
                .stream().map(orderLineItemsDto -> maptoDto(orderLineItemsDto)).collect(Collectors.toList());

      order.setOrderLineItems(orderLineItems);

     List<String >skuCodes= order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());
       InventoryResponse[] inventoryResponsesArray=webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                       uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                      .retrieve()
                              .bodyToMono(InventoryResponse[].class)
                                      .block();
       boolean allProductsinstock= Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
       if (allProductsinstock){

      orderRepository.save(order);
           return "Order Placed Successfully";
       }else{
           throw new IllegalArgumentException("Product is not in Stock please try again later !");
       }

    }
    private OrderLineItems maptoDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems=new OrderLineItems();


        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());

        return orderLineItems;
    }
}
