package com.example.ecom.controllers;

import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    // Hello people
    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        CancelOrderResponseDto response = new CancelOrderResponseDto();
        try {
            response.setOrder(orderService.cancelOrder(
                    cancelOrderRequestDto.getOrderId(),
                    cancelOrderRequestDto.getUserId()
            ));
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
        }

        return response;
    }

}
