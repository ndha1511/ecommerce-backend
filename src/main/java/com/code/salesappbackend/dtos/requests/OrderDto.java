package com.code.salesappbackend.dtos.requests;

import com.code.salesappbackend.models.enums.DeliveryMethod;
import com.code.salesappbackend.models.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderDto {
    @NotNull(message = "user id must be not null")
    private String email;
    @NotNull(message = "payment method must be not null")
    private PaymentMethod paymentMethod;
    private String note;
    @NotNull(message = "address must be not null")
    private AddressDto address;
    @Pattern(message = "phone number is invalid", regexp = "^0\\d{9}")
    @NotBlank(message = "phone number must be not blank")
    private String phoneNumber;
    @NotBlank(message = "buyer name must be not blank")
    private String buyerName;
    @NotNull(message = "delivery method must be not null")
    private DeliveryMethod deliveryMethod;
    @NotEmpty(message = "products order must be not empty")
    private List<ProductOrderDto> productOrders;
    private List<Long> vouchers;


}
